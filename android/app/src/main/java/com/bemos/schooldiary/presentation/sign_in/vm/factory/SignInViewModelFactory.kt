package com.bemos.schooldiary.presentation.sign_in.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.schooldiary.domain.use_case.AuthenticateUserUseCase
import com.bemos.schooldiary.domain.use_case.TokenSetUseCase
import com.bemos.schooldiary.presentation.sign_in.vm.SignInViewModel

class SignInViewModelFactory(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val tokenSetUseCase: TokenSetUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(
            authenticateUserUseCase = authenticateUserUseCase,
            tokenSetUseCase = tokenSetUseCase
        ) as T
    }
}