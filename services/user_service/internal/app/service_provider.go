package app

import (
	"log"
	"user_service/internal/config"
	"user_service/internal/db"
	usersRepository "user_service/internal/repositories/user"
	usersService "user_service/internal/services/user"
)

type serviceProvider struct {
	postgresDB db.PostgresClient

	httpConfig config.HTTPConfig

	groupRepository usersRepository.Repository
	groupService    usersService.Service
}

func newServiceProvider(db db.PostgresClient) *serviceProvider {
	return &serviceProvider{
		postgresDB: db,
	}
}

func (s *serviceProvider) PostgresDB() db.PostgresClient {
	return s.postgresDB
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

func (s *serviceProvider) UserRepository() usersRepository.Repository {
	if s.groupRepository == nil {
		s.groupRepository = usersRepository.NewRepository(s.PostgresDB())
	}
	return s.groupRepository
}

func (s *serviceProvider) UserService() usersService.Service {
	if s.groupService == nil {
		s.groupService = usersService.NewService(s.UserRepository())
	}
	return s.groupService
}
