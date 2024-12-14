package com.bemos.schooldiary.data.remote.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetorofitUserService {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://58e3-158-160-53-213.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}