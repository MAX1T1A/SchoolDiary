package group

import (
	"context"
	group "core_service/internal/repositories/group"
)

type Service interface {
	AddGroup(ctx context.Context) error
	// CreateUser(ctx context.Context, user models.UserRegisterRequest) error
	// AuthenticateUser(ctx context.Context, user models.UserAuthenticateRequest) (*models.UserAuthenticateResponse, error)
}

var _ Service = (*service)(nil)

type service struct {
	groupRepository group.Repository
}

func NewService(groupRepository group.Repository) Service {
	return &service{
		groupRepository: groupRepository,
	}
}
