package users

import (
	"bff_service/internal/api_clients/users"
	"bff_service/internal/config"

	"github.com/gin-gonic/gin"
)

type Facade interface {
	Authenticate(c *gin.Context)
}

var _ Facade = (*facade)(nil)

type facade struct {
	usersApiClient users.ApiClient
	jwtConfig      config.JWTConfig
}

func NewFacade(usersApiClient users.ApiClient, jwtConfig config.JWTConfig) Facade {
	return &facade{
		usersApiClient: usersApiClient,
		jwtConfig:      jwtConfig,
	}
}
