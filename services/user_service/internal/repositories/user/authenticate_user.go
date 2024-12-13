package user

import (
	"context"
	"log"

	"user_service/internal/config/errors"
	"user_service/internal/models"
	"user_service/internal/utils/hasher"

	"github.com/jackc/pgx/v4"
)

func (r *repository) AuthenticateUser(ctx context.Context, user models.UserAuthenticateRequest) (*models.UserAuthenticateResponse, error) {
	var hashedPassword string
	var userAuthenticateResponse models.UserAuthenticateResponse

	row := r.db.DB().QueryRow(
		ctx, `
			SELECT id, password_hash
			FROM users
			WHERE email = $1;
			`,
		user.Login)

	err := row.Scan(&userAuthenticateResponse.ID, &hashedPassword)
	if err != nil {
		println(user.Login)
		log.Printf("ошибка при аутентификации пользователя: %v", err)
		if err == pgx.ErrNoRows {
			return nil, errors.ErrUnauthorized
		}
		return nil, errors.ErrInternalError
	}

	if !hasher.CheckPasswordHash(user.Password, hashedPassword) {
		log.Printf("неверный пароль для пользователя: %v", user.Login)
		return nil, errors.ErrUnauthorized
	}

	return &userAuthenticateResponse, nil
}
