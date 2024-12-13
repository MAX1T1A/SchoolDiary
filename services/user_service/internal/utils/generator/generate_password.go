package generator

import (
	"math/rand"
	"time"
)

var wordList = []string{
	"apple", "grape", "pearl", "stone", "flame", "cloud", "brick", "spark", "shine", "dream",
}

func GeneratePassword() string {
	// Создает пароль, состоящий из двух случайных осмысленных слов длиной 5 символов
	randGen := rand.New(rand.NewSource(time.Now().UnixNano()))
	word1 := wordList[randGen.Intn(len(wordList))]
	word2 := wordList[randGen.Intn(len(wordList))]

	return word1 + word2
}
