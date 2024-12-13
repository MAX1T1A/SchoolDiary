package users

import "bff_service/internal/config"

type ApiClient interface {
	Authenticate(loging string, password string) (UserAuthResponse, error)
}

var _ ApiClient = (*apiClient)(nil)

type apiClient struct {
	serviceMapper config.ServiceMapper
}

func NewApiClient(serviceMapper config.ServiceMapper) ApiClient {
	return &apiClient{serviceMapper: serviceMapper}
}
