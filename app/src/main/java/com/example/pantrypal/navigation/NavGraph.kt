package com.example.pantrypal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*

import com.example.pantrypal.data.database.PantryDatabase
import com.example.pantrypal.data.entity.PantryItem
import com.example.pantrypal.data.entity.User

import com.example.pantrypal.repository.PantryRepository
import com.example.pantrypal.repository.UserRepository

import com.example.pantrypal.ui.additem.AddItemScreen
import com.example.pantrypal.ui.login.LoginScreen
import com.example.pantrypal.ui.pantry.PantryScreen
import com.example.pantrypal.ui.register.RegisterScreen

import com.example.pantrypal.viewmodel.PantryViewModel
import com.example.pantrypal.viewmodel.PantryViewModelFactory
import com.example.pantrypal.viewmodel.UserViewModel
import com.example.pantrypal.viewmodel.UserViewModelFactory

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val context = LocalContext.current

    val database = PantryDatabase.getDatabase(context)

    val userRepository =
        UserRepository(database.userDao())

    val pantryRepository =
        PantryRepository(database.pantryDao())

    val userViewModel: UserViewModel =
        viewModel(
            factory =
                UserViewModelFactory(userRepository)
        )

    val pantryViewModel: PantryViewModel =
        viewModel(
            factory =
                PantryViewModelFactory(
                    pantryRepository
                )
        )

    val pantryItems by
    pantryViewModel.items
        .collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {

            LoginScreen(

                onRegisterClick = {
                    navController.navigate(
                        Screen.Register.route
                    )
                },

                onLoginClick = { email, password ->

                    userViewModel.login(
                        email,
                        password
                    )
                }
            )

            if (userViewModel.loginSuccess.value) {

                navController.navigate(
                    Screen.Pantry.route
                )
            }
        }

        composable(Screen.Register.route) {

            RegisterScreen(
                onRegisterClick = {
                        email,
                        password,
                        pin ->

                    userViewModel.register(
                        User(
                            email = email,
                            password = password,
                            pin = pin
                        )
                    )

                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Pantry.route) {

            PantryScreen(
                items = pantryItems,

                onAddClick = {
                    navController.navigate(
                        Screen.AddItem.route
                    )
                },

                onDeleteClick = { item ->
                    pantryViewModel.deleteItem(item)
                }
            )
        }

        composable(Screen.AddItem.route) {

            AddItemScreen(

                onAddItem = {
                        name,
                        quantity,
                        category ->

                    pantryViewModel.addItem(

                        PantryItem(
                            userId = 1,
                            itemName = name,
                            quantity = quantity,
                            category = category,
                            expirationDate =
                                System.currentTimeMillis()
                        )
                    )

                    navController.popBackStack()
                }
            )
        }
    }
}