package app

import (
	usersApiClient "bff_service/internal/api_clients/users"
	"bff_service/internal/config"
	"bff_service/internal/facades/users"
	"bff_service/internal/handlers/bff"
	"log"
	"time"
)

type serviceProvider struct {
	httpConfig          config.HTTPConfig
	jwtConfig           config.JWTConfig
	serviceMapperConfig config.ServiceMapper

	bffHandler bff.BFFHandler

	usersFacade users.Facade

	usersApiClient usersApiClient.ApiClient
}

func newServiceProvider() *serviceProvider {
	return &serviceProvider{}
}

func (s *serviceProvider) HTTPConfig() config.HTTPConfig {
	if s.httpConfig == nil {
		cfg, err := config.NewHTTPConfig()
		if err != nil {
			log.Fatalf("не удалось получить конфигурацию http: %s", err.Error())
		}

		s.httpConfig = cfg
	}
	return s.httpConfig
}
func (s *serviceProvider) JWTConfig() config.JWTConfig {
	if s.jwtConfig == nil {
		cfg, err := config.NewJWTConfig()
		if err != nil {
			log.Fatalf("не удалось получить конфигурацию jwt: %s", err.Error())
		}

		s.jwtConfig = cfg
	}
	return s.jwtConfig
}

func (s *serviceProvider) BFFHandler() bff.BFFHandler {
	if s.bffHandler == nil {
		s.bffHandler = bff.NewBFFHandlerExperimental(3, 2*time.Second, s.UsersApiClient())
	}

	return s.bffHandler
}

func (s *serviceProvider) UsersFacade() users.Facade {
	if s.usersFacade == nil {
		s.usersFacade = users.NewFacade(s.UsersApiClient(), s.JWTConfig())
	}

	return s.usersFacade
}

func (s *serviceProvider) UsersApiClient() usersApiClient.ApiClient {
	if s.usersApiClient == nil {
		s.usersApiClient = usersApiClient.NewApiClient(s.ServiceMapper())
	}

	return s.usersApiClient
}

func (s *serviceProvider) ServiceMapper() config.ServiceMapper {
	if s.serviceMapperConfig == nil {
		s.serviceMapperConfig = config.NewServiceMapper()
	}

	return s.serviceMapperConfig
}
