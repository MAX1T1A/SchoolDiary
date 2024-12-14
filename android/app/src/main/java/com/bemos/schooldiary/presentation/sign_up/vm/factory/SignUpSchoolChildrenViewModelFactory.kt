package com.bemos.schooldiary.presentation.sign_up.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.schooldiary.domain.use_case.RegisterUserUseCase
import com.bemos.schooldiary.presentation.sign_up.vm.SignUpSchoolChildrenViewModel

class SignUpSchoolChildrenViewModelFactory(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpSchoolChildrenViewModel(
            registerUserUseCase = registerUserUseCase
        ) as T
    }

}