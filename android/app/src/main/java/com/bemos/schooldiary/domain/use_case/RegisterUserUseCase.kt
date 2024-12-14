package com.bemos.schooldiary.domain.use_case

import com.bemos.schooldiary.domain.models.User
import com.bemos.schooldiary.domain.repositories.UserServiceRepository
import retrofit2.Call

class RegisterUserUseCase(
    private val repository: UserServiceRepository
) {
    fun execute(
        user: User
    ): Call<Void> {
        return repository.registerUser(user)
    }
}