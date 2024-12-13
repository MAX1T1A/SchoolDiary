package config

import (
	"net"
	"os"

	"github.com/pkg/errors"
)

const (
	httpHostEnvName = "BFF__HTTP_HOST"
	httpPortEnvName = "BFF__HTTP_PORT"
)

type HTTPConfig interface {
	Address() string
}
type httpConfig struct {
	host string
	port string
}

func NewHTTPConfig() (HTTPConfig, error) {
	host := os.Getenv(httpHostEnvName)
	if len(host) == 0 {
		return nil, errors.New("http host не найден")
	}

	port := os.Getenv(httpPortEnvName)
	if len(port) == 0 {
		return nil, errors.New("http port не найден")
	}

	return &httpConfig{
		host: host,
		port: port,
	}, nil
}
func (cfg *httpConfig) Address() string {
	return net.JoinHostPort(cfg.host, cfg.port)
}
