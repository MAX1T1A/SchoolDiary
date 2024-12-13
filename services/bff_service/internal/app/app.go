package app

import (
	"bff_service/internal/api/users"
	"bff_service/internal/config"
	"bff_service/internal/handlers/bff"
	"bff_service/internal/middleware"
	"context"
	"html/template"
	"log"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	swaggerfiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

type App struct {
	serviceProvider *serviceProvider
	httpRouter      *gin.Engine
}

func NewApp(ctx context.Context) (*App, error) {
	a := &App{}

	err := a.initDeps(ctx)
	if err != nil {
		return nil, err
	}

	return a, nil
}

func (a *App) RunHTTPServer() error {
	return a.runHTTPServer()
}

func (a *App) initDeps(ctx context.Context) error {
	a.initHTTPRouter(ctx)
	a.initServiceProvider(ctx)

	return nil
}

func (a *App) initServiceProvider(_ context.Context) error {
	a.serviceProvider = newServiceProvider()
	return nil
}

func (a *App) initHTTPRouter(_ context.Context) error {
	a.httpRouter = gin.Default()

	// Загрузка шаблонов
	a.httpRouter.SetFuncMap(template.FuncMap{})
	a.httpRouter.LoadHTMLGlob("/code/templates/*")

	jwtCfg, err := config.NewJWTConfig()
	if err != nil {
		return err
	}

	a.httpRouter.GET("/bff/api/docs/*any", ginSwagger.WrapHandler(swaggerfiles.Handler))
	a.httpRouter.GET("/bff/api/microservices", a.renderMicroservicesPage)
	a.httpRouter.Use(middleware.JwtMiddleware(jwtCfg))

	return nil
}

func (a *App) S() *serviceProvider {
	return a.serviceProvider
}

func (a *App) renderMicroservicesPage(c *gin.Context) {
	services := []map[string]string{
		{
			"name":              "❤️ BFF",
			"description":       "Сервис для агрегации данных из других сервисов",
			"documentationLink": "/bff/api/docs/index.html",
			"wikiLink":          "",
		},
		{
			"name":              "🙍‍♂️ Users",
			"description":       "Сервис предназначен для хранения и обработки данных пользователей",
			"documentationLink": "/users/api/docs/index.html",
			"wikiLink":          "",
		},
	}

	c.HTML(http.StatusOK, "docs.html", gin.H{
		"Services": services,
	})
}

func (a *App) runHTTPServer() error {
	log.Printf("HTTP-сервер работает на %s", a.serviceProvider.HTTPConfig().Address())

	users.RegisterUsersRoutes(a.httpRouter, a.serviceProvider.UsersFacade())

	handler := bff.NewBFFHandlerExperimental(1, 2*time.Second, a.serviceProvider.UsersApiClient())
	a.httpRouter.NoRoute(handler.RerouteToAppropriateService)

	return a.httpRouter.Run(a.serviceProvider.HTTPConfig().Address())
}
