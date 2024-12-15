package models

import (
	"errors"
	"user_service/internal/models/validators"
)

type UserRegisterRequest struct {
	Surname    string `json:"surname"`
	Name       string `json:"name"`
	Patronymic string `json:"patronymic"`
	Email      string `json:"email"`
	Role       string `json:"role"`
}

type UserAuthenticateRequest struct {
	Login    string `json:"email"`
	Password string `json:"password"`
}

type UserAuthenticateResponse struct {
	ID   int    `json:"id"`
	Role string `json:"role"`
}

func (c *UserRegisterRequest) UserRegisterValidate() error {
	if !validators.IsValidEmail(c.Email) {
		return errors.New("некорректный email")
	}

	if !validators.IsValidRoleEnum(c.Role) {
		return errors.New("невереное значение role")
	}

	return nil
}

func (c *UserAuthenticateRequest) UserAuthenticateValidate() error {
	if len(c.Password) == 0 || len(c.Password) < 8 {
		return errors.New("пароль не может быть пустой строкой и длинна должна быть больше/равна 8 символам")
	}

	return nil
}
