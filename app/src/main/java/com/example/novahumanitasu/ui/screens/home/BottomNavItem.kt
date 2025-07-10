package com.example.novahumanitasu.ui.screens.home

import androidx.annotation.DrawableRes
import com.example.novahumanitasu.R

sealed class BottomNavItem(
    val title: String,
    val route: String,
    @DrawableRes val icon: Int
){
    object Courses: BottomNavItem("Clases", "home/courses", R.drawable.ic_book)
    object Calendar: BottomNavItem("Calendario", "home/calendar", R.drawable.ic_calendar)
    object Home: BottomNavItem("Inicio", "home/main", R.drawable.ic_home)
    object Notifications : BottomNavItem("Alertas", "home/notifications", R.drawable.ic_notification)
    object More: BottomNavItem("Más", "home/more", R.drawable.ic_more)
}