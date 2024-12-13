package bff

import (
	"fmt"
	"io"
	"log"
	"net/http"
	"net/url"
	"os"
	"strings"
	"time"

	usersApiClient "bff_service/internal/api_clients/users"
	"bff_service/internal/config"

	"github.com/gin-gonic/gin"
)

var (
	DomainMapper = map[string]string{
		"users": os.Getenv(config.UsersHTTPServerUrl),
	}
)

type BFFHandler interface {
	RerouteToAppropriateService(c *gin.Context)
}

type BFFHandlerExperimental struct {
	logger  *log.Logger
	retries int
	delay   time.Duration
	client  *http.Client

	usersApiClient usersApiClient.ApiClient
}

func NewBFFHandlerExperimental(retries int, delay time.Duration, usersApiClient usersApiClient.ApiClient) *BFFHandlerExperimental {
	return &BFFHandlerExperimental{
		logger:  log.Default(),
		retries: retries,
		delay:   delay,
		client:  &http.Client{Timeout: 10 * time.Minute},

		usersApiClient: usersApiClient,
	}
}

func (h *BFFHandlerExperimental) RerouteToAppropriateService(c *gin.Context) {
	resolvedURL, _ := h.resolveAndRemapURL(c.Request.URL.String())
	h.logger.Printf("Созданный URL-адрес: %s", resolvedURL)

	if resolvedURL == "" {
		c.Status(http.StatusNotFound)
		return
	}

	h.makeRequest(c, resolvedURL)
}

func (h *BFFHandlerExperimental) resolveAndRemapURL(urlStr string) (string, string) {
	parsedURL, err := url.Parse(urlStr)
	if err != nil {
		h.logger.Printf("Ошибка при разборе URL: %v", err)
		return "", ""
	}

	serviceName := strings.Split(strings.TrimPrefix(parsedURL.Path, "/"), "/")[0]

	if baseURL, found := DomainMapper[serviceName]; found {
		newURL := fmt.Sprintf("%s%s", baseURL, strings.TrimPrefix(parsedURL.Path, serviceName+"/"))
		if parsedURL.RawQuery != "" {
			newURL = fmt.Sprintf("%s?%s", newURL, parsedURL.RawQuery)
		}
		return newURL, serviceName
	}

	return "", ""
}

func (h *BFFHandlerExperimental) makeRequest(c *gin.Context, resolvedURL string) {
	var (
		response *http.Response
	)

	for attempt := 0; attempt < h.retries; attempt++ {
		req, err := http.NewRequestWithContext(c.Request.Context(), c.Request.Method, resolvedURL, c.Request.Body)
		if err != nil {
			h.logger.Printf("Ошибка при создании запроса: %v", err)
			continue
		}
		req.Header = h.constructHeaders(c)

		response, err = h.client.Do(req)
		if err != nil {
			h.logger.Printf("Ошибка при выполнении запроса: %v", err)
		} else if response.StatusCode != http.StatusInternalServerError {
			h.convertClientResponseToServerResponse(c, response)
			return
		} else {
			h.logger.Printf("Получен 500 ответ, попытка %d из %d", attempt+1, h.retries)
		}

		if attempt < h.retries-1 {
			time.Sleep(h.delay)
		}
	}

	c.Status(http.StatusServiceUnavailable)
}

func (h *BFFHandlerExperimental) constructHeaders(c *gin.Context) http.Header {
	headers := c.Request.Header.Clone()

	headers.Set("host", strings.Replace(headers.Get("host"), "_", "-", -1))

	clientID, exists := c.Get("user_id")
	if !exists {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
			"error": "user_id не найден в контексте",
		})
		return nil
	}

	// Приведение типа с проверкой
	var clientIDInt int
	switch v := clientID.(type) {
	case float64:
		clientIDInt = int(v)
	case int:
		clientIDInt = v
	default:
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{
			"error": "user_id имеет неверный тип",
		})
		return nil
	}

	userData := map[string]string{
		"X-USER-ID": fmt.Sprintf("%v", clientIDInt),
	}

	for key, value := range userData {
		if value != "" {
			headers.Set(key, value)
		} else {
			h.logger.Printf("Не удалось получить данные пользователя из токена")
		}
	}

	return headers
}

func (h *BFFHandlerExperimental) convertClientResponseToServerResponse(c *gin.Context, clientResponse *http.Response) {
	defer clientResponse.Body.Close()

	body, err := io.ReadAll(clientResponse.Body)
	if err != nil {
		h.logger.Printf("Ошибка при чтении ответа: %v", err)
		c.Status(http.StatusInternalServerError)
		return
	}

	for key := range clientResponse.Header {
		if strings.ToLower(key) != "content-length" && strings.ToLower(key) != "host" && strings.ToLower(key) != "transfer-encoding" {
			c.Writer.Header().Set(key, clientResponse.Header.Get(key))
		}
	}

	c.Data(clientResponse.StatusCode, clientResponse.Header.Get("content-type"), body)
}
