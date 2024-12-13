package users

import (
	"bff_service/internal/facades/users"

	"github.com/gin-gonic/gin"
)

func RegisterUsersRoutes(r *gin.Engine, usersFacade users.Facade) {
	users := r.Group("/users/api/v1")

	{
		users.POST("/authenticate", func(c *gin.Context) {
			usersFacade.Authenticate(c)
		})

	}
}
