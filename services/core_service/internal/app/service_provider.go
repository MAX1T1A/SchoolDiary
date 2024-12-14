package app

import (
	"core_service/internal/config"
	"core_service/internal/db"
	groupRepository "core_service/internal/repositories/group"
	groupService "core_service/internal/services/group"
	"log"
)

type serviceProvider struct {
	postgresDB db.PostgresClient

	httpConfig config.HTTPConfig

	groupRepository groupRepository.Repository
	groupService    groupService.Service
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

func (s *serviceProvider) GroupRepository() groupRepository.Repository {
	if s.groupRepository == nil {
		s.groupRepository = groupRepository.NewRepository(s.PostgresDB())
	}
	return s.groupRepository
}

func (s *serviceProvider) GroupService() groupService.Service {
	if s.groupService == nil {
		s.groupService = groupService.NewService(s.GroupRepository())
	}
	return s.groupService
}
