package com.bemos.schooldiary.data.remote

import com.bemos.schooldiary.presentation.sign_up.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserServiceApi {
    @POST("users/api/v1/register")
    fun registerUser(@Body user: User): Call<User>
}