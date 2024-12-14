package com.bemos.schooldiary.presentation.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bemos.schooldiary.presentation.sign_up.vm.SignUpSchoolChildrenViewModel
import com.bemos.schooldiary.presentation.sign_up.vm.factory.SignUpSchoolChildrenViewModelFactory

@Composable
fun SignUpSchoolChildrenScreen(
    modifier: Modifier = Modifier,
    signUpSchoolChildrenViewModelFactory: SignUpSchoolChildrenViewModelFactory
) {

    val viewModel = viewModel<SignUpSchoolChildrenViewModel>(
        factory = signUpSchoolChildrenViewModelFactory
    )

    SignUpSchoolChildrenContent(
        onRegisterClick = { user ->
            viewModel.registerUser(
                user
            )
        }
    )
}