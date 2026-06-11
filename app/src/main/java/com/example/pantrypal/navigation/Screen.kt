package com.example.pantrypal.navigation

sealed class Screen(
    val route: String
) {

    object Login : Screen("login")

    object Register : Screen("register")

    object Pantry : Screen("pantry")

    object Dashboard : Screen("dashboard")

    object AddItem : Screen("addItem")
}