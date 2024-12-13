package middleware

import (
	"bff_service/internal/config"
	"errors"
	"net/http"
	"strings"

	"github.com/gin-gonic/gin"
	jwt "github.com/golang-jwt/jwt/v5"
)

var COOKIE_AUTH = []string{"/path1"}
var NO_HEADER_AUTH_TRIE = []string{
	"/users/api/v1/authenticate",
	"/users/api/v1/register",
	"/bff/api/docs",
	"/users/api/docs",
	"/zulu/api/docs",
	"/core/api/docs",
	"/core/api/openapi.json",
	"/bff/api/microservices"}

func JwtMiddleware(cfg config.JWTConfig) gin.HandlerFunc {
	return func(c *gin.Context) {
		for _, prefix := range NO_HEADER_AUTH_TRIE {
			if strings.HasPrefix(c.Request.URL.Path, prefix) {
				c.Set("user_id", 0)
				c.Next()
				return
			}
		}

		var tokenString string
		// Проверка на наличие JWT в куки
		for _, path := range COOKIE_AUTH {
			if strings.HasPrefix(c.Request.URL.Path, path) {
				if cookie, err := c.Cookie("jwt"); err == nil {
					tokenString = cookie
				} else {
					c.String(http.StatusUnauthorized, "JWT не указан в файлах cookie")
					c.Abort()
					return
				}
				break
			}
		}

		// Проверка заголовка Authorization
		if tokenString == "" {
			authHeader := c.GetHeader("Authorization")
			if strings.HasPrefix(authHeader, "Bearer ") {
				tokenString = strings.TrimPrefix(authHeader, "Bearer ")
			} else {
				c.String(http.StatusUnauthorized, "Заголовок авторизации не найден или не начинается с Bearer")
				c.Abort()
				return
			}
		}

		// Проверка и разбор JWT токена
		token, err := jwt.Parse(tokenString, func(token *jwt.Token) (interface{}, error) {
			// Проверка алгоритма подписи
			if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
				return nil, errors.New("Некорректный метод подписи")
			}
			return []byte(cfg.GetJWTSecret()), nil
		})

		if err != nil {
			var errMsg string
			if errors.Is(err, jwt.ErrTokenExpired) {
				errMsg = "Просроченная подпись"
			} else if errors.Is(err, jwt.ErrSignatureInvalid) {
				errMsg = "Ошибка проверки подписи"
			} else {
				errMsg = "Недействительный JWT"
			}
			c.String(http.StatusUnauthorized, errMsg)
			c.Abort()
			return
		}

		if claims, ok := token.Claims.(jwt.MapClaims); ok && token.Valid {
			c.Set("user_id", claims["user_id"])
			// userID, ok := claims["user_id"]
			// if !ok {
			// 	c.String(http.StatusUnauthorized, "Недействительный JWT")
			// 	c.Abort()
			// }

			// c.Header("X-USER-ID", fmt.Sprintf("%v", userID))
		} else {
			c.String(http.StatusUnauthorized, "Недействительный JWT")
			c.Abort()
			return
		}

		c.Next()
	}
}
