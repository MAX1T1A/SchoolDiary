package com.bemos.schooldiary.domain.repositories

import com.bemos.schooldiary.domain.models.AccessToken
import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.domain.models.User
import retrofit2.Call
import retrofit2.Response

interface UserServiceRepository {
    fun registerUser(user: User): Call<Void>
    suspend fun authenticateUser(authUser: AuthenticateUser): Response<AccessToken>
}