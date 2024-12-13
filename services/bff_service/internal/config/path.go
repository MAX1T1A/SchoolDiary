package config

import (
	"os"
)

const UsersHTTPServerUrl = "USERS_HTTP_SERVICE_URL"

type ServiceMapper interface {
	GetServiceURL(service string) string
}

type serviceMapper struct {
	Users    string
	CoreData string
	Zulu     string
}

func NewServiceMapper() ServiceMapper {
	return &serviceMapper{
		Users: getEnv(UsersHTTPServerUrl, "http://host.docker.internal:8001"),
	}
}

func getEnv(key, fallback string) string {
	if value, exists := os.LookupEnv(key); exists {
		return value
	}
	return fallback
}

func (sm *serviceMapper) GetServiceURL(service string) string {
	switch service {
	case "users":
		return sm.Users
	default:
		return ""
	}
}
