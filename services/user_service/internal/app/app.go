package app

import (
	"context"
	"log"
	v1 "user_service/internal/api/http/v1"
	"user_service/internal/db"
	"user_service/internal/middleware"

	swaggerfiles "github.com/swaggo/files"

	"github.com/gin-gonic/gin"
	ginSwagger "github.com/swaggo/gin-swagger"
)

type App struct {
	serviceProvider *serviceProvider
	httpRouter      *gin.Engine
}

func NewApp(ctx context.Context, db db.PostgresClient) (*App, error) {
	a := &App{}

	err := a.initDeps(ctx, db)
	if err != nil {
		return nil, err
	}

	return a, nil
}

func (a *App) RunHTTPServer() error {
	return a.runHTTPServer()
}

func (a *App) initDeps(ctx context.Context, db db.PostgresClient) error {

	a.initHTTPRouter(ctx)
	a.initServiceProvider(ctx, db)

	return nil
}

func (a *App) initServiceProvider(_ context.Context, db db.PostgresClient) error {
	a.serviceProvider = newServiceProvider(db)
	return nil
}

func (a *App) initHTTPRouter(_ context.Context) error {
	a.httpRouter = gin.Default()

	a.httpRouter.GET("/users/api/docs/*any", ginSwagger.WrapHandler(swaggerfiles.Handler))
	return nil
}

func (a *App) S() *serviceProvider {
	return a.serviceProvider
}

func (a *App) runHTTPServer() error {
	log.Printf("HTTP-сервер работает на %s", a.serviceProvider.HTTPConfig().Address())

	v1.RegisterNoAuthRouter(a.httpRouter, a.serviceProvider.UserService())
	a.httpRouter.Use(middleware.AuthRequiredMiddleware())

	return a.httpRouter.Run(a.serviceProvider.HTTPConfig().Address())
}
