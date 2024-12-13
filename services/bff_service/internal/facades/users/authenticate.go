package users

import (
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	jwt "github.com/golang-jwt/jwt/v5"
)

type UserAuthenticate struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}

type AuthResponse struct {
	Access string `json:"access" example:"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MjYzOTgzOTQsInVzZXJfaWQiOjF9.1yKDMfobTV8pAOK6QFClZLRuOb44KtjoezgbBgvksBQ"`
}

func (u *UserAuthenticate) ValidateUserAuthenticate() error {
	return nil
}

// @Router /users/api/v1/authenticate [post]
// @Summary Аутентификация пользователя по логину и паролю.
// @Description Этот эндпоинт проверяет данные пользователя и возвращает JWT токен.
// @Tags Users
// @Accept  json
// @Produce  json
// @Param request body UserAuthenticate true "Запрос аутентификации пользователя"
// @Success 200 {object} AuthResponse "Успешный ответ, содержащий JWT токен в поле 'access'"
// @Failure 400 {object} map[string]string "Ошибка валидации запроса"
// @Failure 500 {object} map[string]string "Ошибка при генерации токена"
func (f *facade) Authenticate(c *gin.Context) {

	var req UserAuthenticate
	if err := c.ShouldBind(&req); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := req.ValidateUserAuthenticate(); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	resp, err := f.usersApiClient.Authenticate(req.Email, req.Password)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	token, err := generateJWTToken(resp.ID, []byte(f.jwtConfig.GetJWTSecret()))
	if err != nil {
		c.JSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	// c.JSON(http.StatusOK, gin.H{
	// 	"access": token,
	// })
	c.JSON(http.StatusOK, AuthResponse{
		Access: token,
	})
}

func generateJWTToken(userID int, jwtSecret []byte) (string, error) {
	claims := jwt.MapClaims{
		"user_id": userID,
		"exp":     time.Now().Add(time.Hour * 72).Unix(), // Токен истекает через 72 часа
	}

	// Создаем токен с методом подписи HMAC и claims
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	tokenString, err := token.SignedString(jwtSecret)
	if err != nil {
		return "", err
	}

	return tokenString, nil
}

// @Success 200 {object} models.UserAuthenticateResponse "Информация для jwt токена."
