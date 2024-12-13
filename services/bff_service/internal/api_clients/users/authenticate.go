package users

import (
	"bytes"
	"encoding/json"
	"fmt"
	"net/http"
)

type UserAuthResponse struct {
	ID int `json:"id"`
}

func (c *apiClient) Authenticate(login string, password string) (UserAuthResponse, error) {
	url := fmt.Sprintf("%s/users/api/v1/authenticate", c.serviceMapper.GetServiceURL("users"))

	payload := map[string]interface{}{
		"password": password,
		"email":    login,
	}

	jsonPayload, err := json.Marshal(payload)
	if err != nil {
		return UserAuthResponse{}, fmt.Errorf("ошибка маршалинга payload: %w", err)
	}

	req, err := http.NewRequest("POST", url, bytes.NewBuffer(jsonPayload))
	if err != nil {
		return UserAuthResponse{}, fmt.Errorf("ошибка создания запроса: %w", err)
	}

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		return UserAuthResponse{}, fmt.Errorf("ошибка отправки запроса: %w", err)
	}
	defer resp.Body.Close()

	if resp.StatusCode != http.StatusOK {
		return UserAuthResponse{}, fmt.Errorf("не удалось авторизироваться: %s", resp.Status)
	}

	var response UserAuthResponse
	if err := json.NewDecoder(resp.Body).Decode(&response); err != nil {
		return UserAuthResponse{}, fmt.Errorf("ошибка декодирования ответа: %w", err)
	}
	return response, nil
}
