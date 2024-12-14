package com.bemos.schooldiary.data.di.modules

import com.bemos.schooldiary.data.remote.retrofit.api.UserServiceApi
import com.bemos.schooldiary.shared.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): UserServiceApi {
        return retrofit.create(UserServiceApi::class.java)
    }
}