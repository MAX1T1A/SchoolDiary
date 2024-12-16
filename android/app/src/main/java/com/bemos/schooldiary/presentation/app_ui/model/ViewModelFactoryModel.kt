package com.bemos.schooldiary.presentation.app_ui.model

import com.bemos.schooldiary.presentation.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.schooldiary.presentation.sign_up.vm.factory.SignUpSchoolChildrenViewModelFactory

data class ViewModelFactoryModel(
    val signInViewModelFactory: SignInViewModelFactory,
    val signUpSchoolChildrenViewModelFactory: SignUpSchoolChildrenViewModelFactory
)