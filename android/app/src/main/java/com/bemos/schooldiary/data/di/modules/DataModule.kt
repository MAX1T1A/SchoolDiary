package com.bemos.schooldiary.data.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.bemos.schooldiary.data.local.impl.TokenManagerImpl
import com.bemos.schooldiary.data.remote.retrofit.api.UserServiceApi
import com.bemos.schooldiary.data.remote.retrofit.repository.impl.UserServiceRepositoryImpl
import com.bemos.schooldiary.domain.repositories.TokenManagerRepository
import com.bemos.schooldiary.domain.repositories.UserServiceRepository
import com.bemos.schooldiary.domain.use_case.AuthenticateUserUseCase
import com.bemos.schooldiary.domain.use_case.RegisterUserUseCase
import com.bemos.schooldiary.domain.use_case.TokenSetUseCase
import com.bemos.schooldiary.shared.Constants.SHARED_PREF_NAME_TOKEN_SAVER
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideUserServiceRepository(
        api: UserServiceApi
    ): UserServiceRepository {
        return UserServiceRepositoryImpl(api)
    }

    @Provides
    fun provideSharedPreferences(
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREF_NAME_TOKEN_SAVER, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideTokenManager(
        sharedPreferences: SharedPreferences
    ): TokenManagerRepository {
        return TokenManagerImpl(
            sharedPreferences
        )
    }

    @Provides
    fun provideRegisterUserUseCase(
        userServiceRepository: UserServiceRepository
    ): RegisterUserUseCase {
        return RegisterUserUseCase(userServiceRepository)
    }

    @Provides
    fun provideAuthenticateUserUseCase(
        userServiceRepository: UserServiceRepository
    ): AuthenticateUserUseCase {
        return AuthenticateUserUseCase(userServiceRepository)
    }

    @Provides
    fun provideTokenSetUseCase(
        tokenManagerRepository: TokenManagerRepository
    ): TokenSetUseCase {
        return TokenSetUseCase(tokenManagerRepository)
    }

}