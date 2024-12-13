FROM golang:1.22.6

WORKDIR /code

COPY go.mod go.sum ./

RUN go mod download
RUN go install github.com/cosmtrek/air@v1.49.0

COPY . .

RUN go build -o output cmd/main.go

CMD ["air", "-c", ".air.toml"]
