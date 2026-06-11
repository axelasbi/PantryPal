package com.example.pantrypal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.pantrypal.ui.login.LoginScreen
import com.example.pantrypal.ui.register.RegisterScreen
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pantrypal.data.database.PantryDatabase
import com.example.pantrypal.data.entity.User
import com.example.pantrypal.repository.UserRepository
import com.example.pantrypal.viewmodel.UserViewModel
import com.example.pantrypal.viewmodel.UserViewModelFactory
import com.example.pantrypal.ui.pantry.PantryScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val context = LocalContext.current

    val database =
        PantryDatabase.getDatabase(context)

    val repository =
        UserRepository(database.userDao())

    val viewModel: UserViewModel =
        viewModel(
            factory = UserViewModelFactory(repository)
        )

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

                    viewModel.login(
                        email,
                        password
                    )
                }
            )

            if (viewModel.loginSuccess.value) {

                navController.navigate(
                    Screen.Pantry.route
                )
            }
        }

        composable(Screen.Register.route) {

            RegisterScreen(
                onRegisterClick = { email, password, pin ->

                    viewModel.register(
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

        composable(
            Screen.Pantry.route
        ) {
            PantryScreen()
        }
    }
}