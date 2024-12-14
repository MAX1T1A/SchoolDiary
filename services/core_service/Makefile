# Makefile для управления проектом на Docker.
#
# Инструкции:
# - Для добавления новой команды определите новую цель и добавьте комментарий перед ней,
#   чтобы описание было видно при вызове 'make help'.
# - Для использования docker-compose с определенными настройками используйте функцию DOCKER_COMPOSE_UP.
#
# Пример: 
#
# # my-command: Мое описание для команды
# my-command: 
#   <ваш код команды>

# Задаем переменные с именами файлов и переменных окружения
LOCAL_ENV_FILE := env/local.sh
TEST_ENV_FILE := env/test.sh

LOCAL_COMPOSE_FILE := docker/local-docker-compose.yml
TEST_COMPOSE_FILE := docker/test-docker-compose.yml

# Цвета для вывода сообщений
RED=\033[0;31m
YELLOW=\033[0;33m
GREEN=\033[0;32m
NC=\033[0m # No Color

# Определяем функции
define DOCKER_COMPOSE_UP
source $(1) && docker compose -f $(2) up $(3) -d
endef

# Базовые команды

## local: Запуск локальной среды
local:
	$(call DOCKER_COMPOSE_UP,$(LOCAL_ENV_FILE),$(LOCAL_COMPOSE_FILE),)

## test: Запуск тестов
test:
	$(call DOCKER_COMPOSE_UP,$(TEST_ENV_FILE),$(TEST_COMPOSE_FILE),--build)

# Дополнительные команды для сборки и запуска

## local-build: Запуск локальной среды с опцией сборки
local-build:
	$(call DOCKER_COMPOSE_UP,$(LOCAL_ENV_FILE),$(LOCAL_COMPOSE_FILE),--build)

## docs: Генерация документации
# go install github.com/swaggo/swag/cmd/swag@latest
# export PATH=$PATH:$(go env GOPATH)/bin
doc:
	swag init -g cmd/main.go

help:
	@echo "${GREEN}Доступные команды:${NC}"
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//' | awk -F ': ' '{printf "${YELLOW}%-20s${NC} %s\n", $$1, $$2}'
	@echo ""
