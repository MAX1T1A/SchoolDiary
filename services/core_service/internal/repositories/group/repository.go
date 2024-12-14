package group

import (
	"context"
	"core_service/internal/db"
)

type Repository interface {
	AddGroup(ctx context.Context) error
	// CreateUser(ctx context.Context, user models.UserRegisterRequest) error
	// AuthenticateUser(ctx context.Context, user models.UserAuthenticateRequest) (*models.UserAuthenticateResponse, error)
}

var _ Repository = (*repository)(nil)

type repository struct {
	db db.PostgresClient
}

func NewRepository(db db.PostgresClient) *repository {
	return &repository{
		db: db,
	}
}
