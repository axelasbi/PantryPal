package com.example.pantrypal.navigation

sealed class Screen(
    val route: String
) {

    object Login : Screen("login")

    object Register : Screen("register")

    object Pantry : Screen("pantry")

    object AddItem : Screen("addItem")

    object EditItem : Screen("editItem")

    object Dashboard : Screen("dashboard")
}