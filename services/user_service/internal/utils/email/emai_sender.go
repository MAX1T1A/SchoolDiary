package email

import (
	"crypto/tls"
	"fmt"
	"log"
	"net/smtp"
	"strings"
)

func SendePasswordOnEmail(clientPassword string, clientEmail string) error {
	// Учетные данные
	smtpServer := "smtp.yandex.ru"
	port := "465" // Для SSL
	email := "max1t1a@yandex.ru"
	password := "ysaxhivoaetocgwp"

	// Установка соединения с сервером
	auth := smtp.PlainAuth("", email, password, smtpServer)

	// Подключение через SSL
	tlsConfig := &tls.Config{
		InsecureSkipVerify: true,
		ServerName:         smtpServer,
	}

	conn, err := tls.Dial("tcp", smtpServer+":"+port, tlsConfig)
	if err != nil {
		log.Fatalf("Не удалось подключиться: %v", err)
		return err
	}
	defer conn.Close()

	client, err := smtp.NewClient(conn, smtpServer)
	if err != nil {
		log.Fatalf("Ошибка клиента SMTP: %v", err)
		return err
	}

	// Аутентификация
	if err := client.Auth(auth); err != nil {
		log.Fatalf("Ошибка аутентификации: %v", err)
		return err
	}

	// Установка отправителя и получателей
	if err := client.Mail(email); err != nil {
		log.Fatalf("Ошибка установки отправителя: %v", err)
		return err
	}

	if err := client.Rcpt(clientEmail); err != nil {
		log.Fatalf("Ошибка добавления получателя: %v", err)
		return err
	}

	// Отправка сообщения
	wc, err := client.Data()
	if err != nil {
		log.Fatalf("Ошибка отправки данных: %v", err)
		return err
	}

	message := strings.Join([]string{
		fmt.Sprintf("From: %s <%s>", "School Diary", email),
		fmt.Sprintf("To: %s", clientEmail),
		fmt.Sprintf("Subject: %s", "Ваш пароль для входа"),
		"MIME-Version: 1.0",
		"Content-Type: text/plain; charset=\"UTF-8\"",
		"",
		fmt.Sprintf("Ваш пароль для входа в личный кабинет: %s", clientPassword),
	}, "\r\n")

	_, err = wc.Write([]byte("" + message))
	if err != nil {
		log.Fatalf("Ошибка записи сообщения: %v", err)
		return err
	}

	if err := wc.Close(); err != nil {
		log.Fatalf("Ошибка закрытия записи: %v", err)
		return err
	}

	// Завершение
	if err := client.Quit(); err != nil {
		log.Fatalf("Ошибка завершения соединения: %v", err)
		return err
	}

	log.Println("Сообщение успешно отправлено!")
	return nil
}
