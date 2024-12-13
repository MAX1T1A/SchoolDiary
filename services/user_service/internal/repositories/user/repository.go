package user

import (
	"context"
	"user_service/internal/db"
	"user_service/internal/models"
)

type Repository interface {
	CreateUser(ctx context.Context, user models.UserRegisterRequest) error
	AuthenticateUser(ctx context.Context, user models.UserAuthenticateRequest) (*models.UserAuthenticateResponse, error)
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
