package com.bemos.schooldiary.presentation.sign_in

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bemos.schooldiary.presentation.sign_in.vm.SignInViewModel
import com.bemos.schooldiary.presentation.sign_in.vm.factory.SignInViewModelFactory

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    signInViewModelFactory: SignInViewModelFactory
) {

    val viewModel = viewModel<SignInViewModel>(
        factory = signInViewModelFactory
    )

    SignInContent(
        onSignInClick = { authenticateUser ->
            viewModel.authUser(
                authUser = authenticateUser
            )
        }
    )
}