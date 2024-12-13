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
}

type UserAuthenticateRequest struct {
	Login    string `json:"email"`
	Password string `json:"password"`
}

type UserAuthenticateResponse struct {
	ID int `json:"id"`
}

func (c *UserRegisterRequest) UserRegisterValidate() error {
	if !validators.IsValidEmail(c.Email) {
		return errors.New("некорректный email")
	}

	return nil
}

func (c *UserAuthenticateRequest) UserAuthenticateValidate() error {
	if len(c.Password) == 0 || len(c.Password) < 8 {
		return errors.New("пароль не может быть пустой строкой и длинна должна быть больше/равна 8 символам")
	}

	return nil
}
