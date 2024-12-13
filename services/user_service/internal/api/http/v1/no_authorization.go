package v1

import (
	"net/http"

	"user_service/internal/config/errors"
	"user_service/internal/models"

	"user_service/internal/services/user"

	"github.com/gin-gonic/gin"
)

func RegisterNoAuthRouter(r *gin.Engine, userService user.Service) {
	user := r.Group("/users/api/v1")
	{
		user.POST("/register", func(ctx *gin.Context) {
			registerUser(ctx, userService)
		})
		user.POST("/authenticate", func(ctx *gin.Context) {
			authenticateUser(ctx, userService)
		})

	}

}

func registerUser(ctx *gin.Context, userService user.Service) {
	var req models.UserRegisterRequest
	if err := ctx.ShouldBindJSON(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := req.UserRegisterValidate(); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := userService.CreateUser(ctx, req); err != nil {
		resp := errors.GetHTTPStatus(err)
		ctx.JSON(resp.Status, gin.H{"error": resp.Message})
		return

	}

	ctx.Status(http.StatusCreated)
}

// @Router /users/api/v1/authenticate [post]
// @Summary Аутентификация пользователя по логину и паролю.
// @Description Этот эндпоинт проверить данные пользователя и вернуть ответ на бфф.
// @Tags Internal
// @Accept  json
// @Produce  json
// @Param request body models.UserAuthenticateRequest true "Запрос аутентификации пользователя"
// @Success 200 {object} models.UserAuthenticateResponse "Информация для jwt токена."
func authenticateUser(ctx *gin.Context, userService user.Service) {
	var req models.UserAuthenticateRequest
	if err := ctx.ShouldBindJSON(&req); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	if err := req.UserAuthenticateValidate(); err != nil {
		ctx.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	userAuthenticateResponse, err := userService.AuthenticateUser(ctx, req)
	if err != nil {
		resp := errors.GetHTTPStatus(err)
		ctx.JSON(resp.Status, gin.H{"error": resp.Message})
		return
	}

	ctx.JSON(http.StatusOK, userAuthenticateResponse)
}
