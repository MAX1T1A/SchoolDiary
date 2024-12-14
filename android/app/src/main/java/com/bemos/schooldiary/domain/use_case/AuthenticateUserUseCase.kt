package com.bemos.schooldiary.domain.use_case

import com.bemos.schooldiary.domain.models.AccessToken
import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.domain.repositories.UserServiceRepository
import retrofit2.Response

class AuthenticateUserUseCase(
    private val repository: UserServiceRepository
) {
    suspend fun execute(
        authUser: AuthenticateUser
    ): Response<AccessToken> {
        return repository.authenticateUser(
            authUser
        )
    }
}