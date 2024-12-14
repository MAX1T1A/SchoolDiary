package com.bemos.schooldiary.presentation.di

import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.domain.use_case.AuthenticateUserUseCase
import com.bemos.schooldiary.domain.use_case.RegisterUserUseCase
import com.bemos.schooldiary.domain.use_case.TokenSetUseCase
import com.bemos.schooldiary.presentation.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.schooldiary.presentation.sign_up.vm.factory.SignUpSchoolChildrenViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideSignUpSchoolChildrenViewModelFactory(
        registerUserUseCase: RegisterUserUseCase
    ) : SignUpSchoolChildrenViewModelFactory {
        return SignUpSchoolChildrenViewModelFactory(
            registerUserUseCase
        )
    }

    @Provides
    fun provideSignInViewModelFactory(
        authenticateUserUseCase: AuthenticateUserUseCase,
        tokenSetUseCase: TokenSetUseCase
    ) : SignInViewModelFactory {
        return SignInViewModelFactory(
            authenticateUserUseCase,
            tokenSetUseCase
        )
    }

}