package com.bemos.schooldiary.domain.use_case

import com.bemos.schooldiary.domain.repositories.TokenManagerRepository

class TokenSetUseCase(
    private val repository: TokenManagerRepository
) {
    fun execute(
        email: String,
        token: String
    ) {
        repository.set(
            token = token,
            email = email
        )
    }
}