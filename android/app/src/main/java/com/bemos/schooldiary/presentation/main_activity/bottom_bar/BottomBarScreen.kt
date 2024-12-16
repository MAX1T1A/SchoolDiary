package com.bemos.schooldiary.presentation.main_activity.bottom_bar

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.bemos.schooldiary.presentation.ui.utils.bottom_bar.BottomBar
import com.bemos.schooldiary.shared.Constants.NAV_SIGN_IN
import com.bemos.schooldiary.shared.Constants.NAV_SIGN_UP_CHILD

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
            NAV_SIGN_UP_CHILD -> false
            NAV_SIGN_IN -> false
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