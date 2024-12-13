package main

import (
	_ "bff_service/docs"
	"bff_service/internal/app"

	"context"
	"log"
	"os"
	"os/signal"
	"syscall"
)

// @title BFF Service API
// @version 1.0
// @description Оптимизированный Gateway под frontend
func main() {
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	// Обработка сигналов ОС
	signals := make(chan os.Signal, 1)
	signal.Notify(signals, syscall.SIGINT, syscall.SIGTERM)

	go func() {
		<-signals
		cancel() // Отправляем сигнал для отмены контекста
	}()

	application, err := app.NewApp(ctx)
	if err != nil {
		log.Fatalf("ошибка инициализации сервиса: %s", err.Error())
	}

	err = application.RunHTTPServer()
	if err != nil {
		log.Fatalf("ошибка запуска сервиса: %v", err)
	}
}
