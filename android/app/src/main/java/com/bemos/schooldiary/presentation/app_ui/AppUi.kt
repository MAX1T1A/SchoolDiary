package com.bemos.schooldiary.presentation.app_ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bemos.schooldiary.presentation.app_ui.model.ViewModelFactoryModel
import com.bemos.schooldiary.presentation.sign_in.SignInScreen
import com.bemos.schooldiary.presentation.sign_up.SignUpSchoolChildrenScreen
import com.bemos.schooldiary.shared.Constants.NAV_SIGN_IN
import com.bemos.schooldiary.shared.Constants.NAV_SIGN_UP_CHILD

@Composable
fun AppUi(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModelFactoryModel: ViewModelFactoryModel,
    onBottomNavVisible: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NAV_SIGN_IN
    ) {
        signIn(
            navController = navController,
            viewModelFactoryModel = viewModelFactoryModel,
            onBottomNavVisible = onBottomNavVisible
        )
        signUpSchoolChildren(
            navController = navController,
            viewModelFactoryModel = viewModelFactoryModel,
            onBottomNavVisible = onBottomNavVisible
        )
    }
}


private fun NavGraphBuilder.signIn(
    navController: NavController,
    viewModelFactoryModel: ViewModelFactoryModel,
    onBottomNavVisible: (Boolean) -> Unit
) {
    composable(
        route = NAV_SIGN_IN
    ) {
        onBottomNavVisible(false)
        SignInScreen(
            signInViewModelFactory = viewModelFactoryModel.signInViewModelFactory
        )
    }
}

private fun NavGraphBuilder.signUpSchoolChildren(
    navController: NavController,
    viewModelFactoryModel: ViewModelFactoryModel,
    onBottomNavVisible: (Boolean) -> Unit
) {
    composable(
        route = NAV_SIGN_UP_CHILD
    ) {
        onBottomNavVisible(false)
        SignUpSchoolChildrenScreen(
            signUpSchoolChildrenViewModelFactory = viewModelFactoryModel.signUpSchoolChildrenViewModelFactory
        )
    }
}