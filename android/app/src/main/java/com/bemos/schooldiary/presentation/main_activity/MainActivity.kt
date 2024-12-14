package com.bemos.schooldiary.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bemos.schooldiary.presentation.di.app_component.appComponent
import com.bemos.schooldiary.presentation.sign_in.SignInScreen
import com.bemos.schooldiary.presentation.sign_up.SignUpSchoolChildrenScreen
import com.bemos.schooldiary.domain.models.User
import com.bemos.schooldiary.presentation.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.schooldiary.presentation.sign_up.vm.factory.SignUpSchoolChildrenViewModelFactory
import com.bemos.schooldiary.presentation.ui.utils.bottom_bar.BottomBar
import com.bemos.schooldiary.shared.Constants.NAV_SIGN_IN
import com.bemos.schooldiary.shared.Constants.NAV_SIGN_UP_CHILD
import com.bemos.schooldiary.ui.theme.SchoolDiaryTheme
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
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
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = NAV_SIGN_IN
                    ) {
                        composable(
                            route = NAV_SIGN_UP_CHILD
                        ) {
                            isBottomBarVisible = false
                            SignUpSchoolChildrenScreen(
                                signUpSchoolChildrenViewModelFactory = signUpSchoolChildrenViewModelFactory
                            )
                        }
                        composable(
                            route = NAV_SIGN_IN
                        ) {
                            isBottomBarVisible = false
                            SignInScreen(
                                signInViewModelFactory = signInViewModelFactory
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBarScreen(
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>
) {
    val bottomBarState by remember {
        mutableStateOf(bottomBarState)
    }
    var selectedItemIndex by remember {
        mutableStateOf(1)
    }
    val items = listOf(
        BottomBar.Home,
        BottomBar.Calendar,
        BottomBar.HomeTask,
        BottomBar.Circle,
        BottomBar.Profile
    )

    navController.addOnDestinationChangedListener { _, destination, _ ->
        bottomBarState.value = when (destination.route) {
            "home" -> false
            "calendar" -> false
            "homeTask" -> false
            "circle" -> false
            "profile" -> false
            else -> true
        }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.title)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (index == selectedItemIndex) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            }
                        ),
                        contentDescription = null
                    )
                }
            )
        }
    }
}