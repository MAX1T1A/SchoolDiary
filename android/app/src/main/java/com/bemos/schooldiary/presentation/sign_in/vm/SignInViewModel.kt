package com.bemos.schooldiary.presentation.sign_in.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bemos.schooldiary.domain.models.AuthenticateUser
import com.bemos.schooldiary.domain.use_case.AuthenticateUserUseCase
import com.bemos.schooldiary.domain.use_case.TokenSetUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val tokenSetUseCase: TokenSetUseCase
) : ViewModel() {

    fun authUser(
        authUser: AuthenticateUser
    ) = viewModelScope.launch {
        val response = authenticateUserUseCase.execute(
            authUser
        )
        if (response.isSuccessful) {
            val accessToken = response.body()?.access
            tokenSetUseCase.execute(
                authUser.email,
                accessToken.toString()
            )
        }
    }

}