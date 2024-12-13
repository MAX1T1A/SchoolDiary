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

# Задаем переменные с имеcdнами файлов и переменных окружения
LOCAL_ENV_FILE := env/local.sh

LOCAL_COMPOSE_FILE := docker/local-docker-compose.yml

# Определяем функции
define DOCKER_COMPOSE_UP
source $(1) && docker compose -f $(2) up $(3) -d
endef

# Базовые команды

## migration-up: Запуск миграции базы данных
migration-up: check-psql_url
	cd internal && migrate -database ${USER__POSTGRESQL_URL} -path db/migrations up

## migration-down: Откат миграции базы данных
migration-down: check-psql_url
	cd internal && migrate -database ${USER__POSTGRESQL_URL} -path db/migrations down

## migration-generate: Генерация миграции, пример: make migration-generate name=test_migration_name
migration-generate: check-psql_url
	cd internal && migrate create -ext sql -dir db/migrations $(name)

## local: Запуск локальной среды
local:
	$(call DOCKER_COMPOSE_UP,$(LOCAL_ENV_FILE),$(LOCAL_COMPOSE_FILE),)

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
	@echo "Доступные команды:"
	@echo ""
	@fgrep -h "##" $(MAKEFILE_LIST) | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'
	@echo ""

check-psql_url:
	@if [ -z "${USER__POSTGRESQL_URL}" ]; then \
        echo "${RED}Ошибка: POSTGRESQL_URL не установлен.${NC}"; \
        echo "${YELLOW}Решение: выполните команду 'source env/out_docker.sh' для установки переменных окружения${NC}"; \
        echo "${YELLOW}или используйте export POSTGRESQL_URL=...${NC}"; \
        exit 1; \
    fi