package user

import (
	"context"
	"user_service/internal/models"
)

func (r *service) AuthenticateUser(ctx context.Context, user models.UserAuthenticateRequest) (*models.UserAuthenticateResponse, error) {

	userAuthenticateResponse, err := r.userRepository.AuthenticateUser(ctx, user)
	if err != nil {
		return nil, err
	}

	return userAuthenticateResponse, nil

}
