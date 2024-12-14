package com.bemos.schooldiary.data.remote.retrofit.repository.impl

import com.bemos.schooldiary.data.remote.retrofit.api.UserServiceApi
import com.bemos.schooldiary.domain.models.AccessToken
import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.domain.models.User
import com.bemos.schooldiary.domain.repositories.UserServiceRepository
import retrofit2.Call
import retrofit2.Response

class UserServiceRepositoryImpl(
    private val api: UserServiceApi
) : UserServiceRepository {

    override fun registerUser(user: User): Call<Void> {
        return api.registerUser(
            user
        )
    }

    override suspend fun authenticateUser(authUser: AuthenticateUser): Response<AccessToken> {
        return api.authenticateUser(
            authUser
        )
    }

}