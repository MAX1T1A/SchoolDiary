package user

import (
	"context"
	"user_service/internal/models"
)

func (r *service) CreateUser(ctx context.Context, user models.UserRegisterRequest) error {

	err := r.userRepository.CreateUser(ctx, user)
	if err != nil {
		return err
	}

	return nil

}
