package com.bemos.schooldiary.presentation.ui.utils.bottom_bar

import com.bemos.schooldiary.R

sealed class BottomBar(
    val title: String,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    object Home : BottomBar(
        title = "home",
        selectedIcon = R.drawable.rounded_school_24,
        unselectedIcon = R.drawable.rounded_school_24,
    )

    object Calendar : BottomBar(
        title = "calendar",
        selectedIcon = R.drawable.rounded_calendar_month_24,
        unselectedIcon = R.drawable.rounded_calendar_month_24,
    )

    object HomeTask : BottomBar(
        title = "homeTask",
        selectedIcon = R.drawable.rounded_add_home_work_24,
        unselectedIcon = R.drawable.rounded_add_home_work_24,
    )

    object Circle : BottomBar(
        title = "circle",
        selectedIcon = R.drawable.rounded_stars_24,
        unselectedIcon = R.drawable.rounded_stars_24,
    )

    object Profile : BottomBar(
        title = "profile",
        selectedIcon = R.drawable.rounded_account_circle_24,
        unselectedIcon = R.drawable.rounded_account_circle_24,
    )
}