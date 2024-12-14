package com.bemos.schooldiary

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
import com.bemos.schooldiary.data.remote.UserServiceApi
import com.bemos.schooldiary.data.remote.retrofit.RetorofitUserService
import com.bemos.schooldiary.presentation.sign_in.SignInScreen
import com.bemos.schooldiary.presentation.sign_up.SignUpSchoolChildrenScreen
import com.bemos.schooldiary.presentation.sign_up.model.User
import com.bemos.schooldiary.presentation.ui.utils.bottom_bar.BottomBar
import com.bemos.schooldiary.ui.theme.SchoolDiaryTheme
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SchoolDiaryTheme {
                val navController = rememberNavController()
                var bottomBarState = remember {
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
                        startDestination = "signIn"
                    ) {
                        composable(
                            route = "signUpChildren"
                        ) {
                            isBottomBarVisible = false
                            SignUpSchoolChildrenScreen()
                        }
                        composable(
                            route = "signIn"
                        ) {
                            isBottomBarVisible = false
                            SignInScreen()
                        }
                    }
                }
            }
        }
    }
}
fun main() {
//    val retrofit = RetorofitUserService().getRetrofit()
//    val api = retrofit.create(UserServiceApi::class.java)
//
//
//    val user = User(
//        surname = "Османов",
//        name = "Микаил",
//        patronymic = "Альбертович",
//        email = "osmanov.design@yandex.ru"
//    )
//
//    val call = api.registerUser(user)
//
//    call.enqueue(object : retrofit2.Callback<User> {
//
//        override fun onResponse(p0: retrofit2.Call<User>, p1: Response<User>) {
//            println("successfully ${p1.message()}")
//        }
//
//        override fun onFailure(p0: retrofit2.Call<User>, p1: Throwable) {
//            println("error")
//        }
//    })

    val client = OkHttpClient()

    // Создаем объект пользователя
    val user = User(
        surname = "Османов",
        name = "Микаил",
        patronymic = "Альбертович",
        email = "osmanov.design@yandex.ru"
    )

    val json = Gson().toJson(user)

    val body = RequestBody.create(
        "application/json; charset=utf-8".toMediaType(),
        json
    )

    val request = Request.Builder()
        .url("")  // Указываем URL
        .get()
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                println("successfully!")
                println("response -> " + response.body?.string())
            } else {
                println("Registration failed: ${response.code} ${response.message}")
            }
        }

        override fun onFailure(call: Call, e: IOException) {
            println("Error: ${e.message}")
        }
    })

//    val client = OkHttpClient()
//
//    // Создаем GET-запрос
//    val request = Request.Builder()
//        .url("https://d4f3-158-160-53-213.ngrok-free.app/users/api/v1/unprotected")  // Указываем URL
//        .get()  // Метод GET
//        .build()
//
//    // Выполняем запрос асинхронно
//    client.newCall(request).enqueue(object : Callback {
//        override fun onResponse(call: Call, response: Response) {
//            if (response.isSuccessful) {
//                println("Response: ${response.body?.string()}")
//            } else {
//                println("Request failed with code: ${response.code}")
//            }
//        }
//
//        override fun onFailure(call: Call, e: IOException) {
//            println("Error: ${e.message}")
//        }
//    })
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