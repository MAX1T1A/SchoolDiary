package com.bemos.schooldiary.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bemos.schooldiary.presentation.app_ui.AppUi
import com.bemos.schooldiary.presentation.app_ui.model.ViewModelFactoryModel
import com.bemos.schooldiary.presentation.di.app_component.appComponent
import com.bemos.schooldiary.presentation.main_activity.bottom_bar.BottomBarScreen
import com.bemos.schooldiary.presentation.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.schooldiary.presentation.sign_up.vm.factory.SignUpSchoolChildrenViewModelFactory
import com.bemos.schooldiary.ui.theme.SchoolDiaryTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var signUpSchoolChildrenViewModelFactory: SignUpSchoolChildrenViewModelFactory

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        appComponent.inject(this)

        setContent {
            SchoolDiaryTheme {
                val navController = rememberNavController()
                val bottomBarState = remember {
                    mutableStateOf(true)
                }
                var isBottomBarVisible by remember {
                    mutableStateOf(false)
                }
                val viewModelFactoryModel = ViewModelFactoryModel(
                    signInViewModelFactory = signInViewModelFactory,
                    signUpSchoolChildrenViewModelFactory = signUpSchoolChildrenViewModelFactory
                )
                Scaffold(
                    bottomBar = {
                        if (isBottomBarVisible) {
                            BottomBarScreen(
                                navController = navController,
                                bottomBarState = bottomBarState
                            )
                        }
                    }
                ) {
                    AppUi(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        viewModelFactoryModel = viewModelFactoryModel
                    ) { visibility ->
                        isBottomBarVisible = visibility
                    }
                }
            }
        }
    }
}