package errors

import (
	"errors"
	"net/http"
	"strconv"
)

var (
	ErrNotFound       = NewErrorResponse(http.StatusNotFound, "Объект не найден")
	ErrInvalidInput   = NewErrorResponse(http.StatusBadRequest, "Некорректные данные")
	ErrUnauthorized   = NewErrorResponse(http.StatusUnauthorized, "Доступ запрещен")
	ErrInternalError  = NewErrorResponse(http.StatusInternalServerError, "Внутренняя ошибка сервера")
	ErrDuplicateEntry = NewErrorResponse(http.StatusConflict, "Запись уже существует")
)

type ErrorResponse struct {
	Status  int    `json:"-"`
	Message string `json:"message"`
}

func (e *ErrorResponse) Error() string {
	return e.Message
}

func NewErrorResponse(status int, message string) *ErrorResponse {
	return &ErrorResponse{
		Status:  status,
		Message: message,
	}
}

// Функция для создания детализированных сообщений об ошибках
func NotFoundWithDetails(object string, id int) *ErrorResponse {
	return &ErrorResponse{
		Status:  http.StatusNotFound,
		Message: object + " с ID " + strconv.Itoa(id) + " не найден",
	}
}

func InvalidInputWithDetails(field string) *ErrorResponse {
	return &ErrorResponse{
		Status:  http.StatusBadRequest,
		Message: "Некорректное значение поля: " + field,
	}
}

func UnauthorizedDetails(message string) *ErrorResponse {
	return &ErrorResponse{
		Status:  http.StatusUnauthorized,
		Message: message,
	}
}

func DuplicateWithDetails(message string) *ErrorResponse {
	return &ErrorResponse{
		Status:  http.StatusConflict,
		Message: message,
	}
}

// GetHTTPStatus возвращает HTTP-статус для предопределенных ошибок
func GetHTTPStatus(err error) *ErrorResponse {
	var e *ErrorResponse
	if errors.As(err, &e) {
		return e
	}

	return ErrInternalError
}
