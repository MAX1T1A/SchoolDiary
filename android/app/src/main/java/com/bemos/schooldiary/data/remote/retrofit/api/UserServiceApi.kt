package com.bemos.schooldiary.data.remote.retrofit.api

import com.bemos.schooldiary.domain.models.AccessToken
import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.domain.models.User
import com.bemos.schooldiary.shared.Constants.USERS_AUTHENTICATE
import com.bemos.schooldiary.shared.Constants.USERS_REGISTER
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserServiceApi {
    @POST(USERS_REGISTER)
    fun registerUser(@Body user: User): Call<Void>

    @POST(USERS_AUTHENTICATE)
    suspend fun authenticateUser(@Body authUser: AuthenticateUser): Response<AccessToken>
}