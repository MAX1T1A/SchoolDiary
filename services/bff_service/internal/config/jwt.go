package config

import (
	"os"

	"github.com/pkg/errors"
)

const JWTSecret = "JWT_SECRET"

type JWTConfig interface {
	GetJWTSecret() string
}

type jwtConfig struct {
	JWTSecret string
}

func NewJWTConfig() (JWTConfig, error) {
	secret := os.Getenv(JWTSecret)
	if len(secret) == 0 {
		return nil, errors.New("jwt secret не найден")
	}

	return jwtConfig{
		JWTSecret: secret,
	}, nil

}

func (c jwtConfig) GetJWTSecret() string {
	return c.JWTSecret
}
