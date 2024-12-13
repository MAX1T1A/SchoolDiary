package middleware

import (
	"fmt"
	"net/http"

	"github.com/gin-gonic/gin"
)

func AuthRequiredMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		subjectID := c.Request.Header.Get("X-USER-ID")
		fmt.Println(subjectID)

		if subjectID == "" || subjectID == "0" {
			c.JSON(http.StatusUnauthorized, gin.H{"error": "Unauthorized"})
			c.Abort()
			return
		}
	}
}
