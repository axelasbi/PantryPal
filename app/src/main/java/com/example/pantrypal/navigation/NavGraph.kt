package com.example.pantrypal.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.compose.runtime.*

import com.example.pantrypal.data.database.PantryDatabase
import com.example.pantrypal.data.entity.PantryItem
import com.example.pantrypal.data.entity.User

import com.example.pantrypal.repository.PantryRepository
import com.example.pantrypal.repository.UserRepository

import com.example.pantrypal.ui.additem.AddItemScreen
import com.example.pantrypal.ui.login.LoginScreen
import com.example.pantrypal.ui.pantry.PantryScreen
import com.example.pantrypal.ui.register.RegisterScreen
import com.example.pantrypal.ui.edititem.EditItemScreen
import com.example.pantrypal.ui.dashboard.DashboardScreen

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

    var selectedItem by remember {
        mutableStateOf<PantryItem?>(null)
    }

    val pantryItems by
    pantryViewModel.items
        .collectAsStateWithLifecycle()

    val totalItems =
        pantryItems.size

    val categories =
        pantryItems
            .map { it.category }
            .distinct()
            .size

    val expiringSoon =
        pantryItems.count {

            val daysLeft =
                kotlin.math.ceil(
                    (
                            it.expirationDate -
                                    System.currentTimeMillis()
                            ).toDouble() /
                            (24 * 60 * 60 * 1000)
                ).toInt()

            daysLeft in 1..3
        }

    val expiredItems =
        pantryItems.count {

            it.expirationDate <
                    System.currentTimeMillis()
        }

    val totalQuantity =
        pantryItems.sumOf {
            it.quantity
        }

    val topCategory =
        pantryItems
            .groupingBy { it.category }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
            ?: "None"

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

                userViewModel.currentUser.value?.let {

                    pantryViewModel.loadItems(
                        it.id
                    )
                }

                navController.navigate(
                    Screen.Pantry.route
                )
            }
        }

        composable(Screen.Register.route) {

            RegisterScreen(
                onRegisterClick = {
                        email,
                        password ->

                    userViewModel.register(
                        User(
                            email = email,
                            password = password
                        )
                    )

                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Pantry.route) {

            PantryScreen(
                items = pantryItems,

                onDashboardClick = {
                    navController.navigate(
                        Screen.Dashboard.route
                    )
                },

                onAddClick = {
                    navController.navigate(
                        Screen.AddItem.route
                    )
                },

                onDeleteClick = { item ->
                    pantryViewModel.deleteItem(item)
                },

                onEditClick = { item ->

                    selectedItem = item

                    navController.navigate(
                        Screen.EditItem.route
                    )
                }
            )
        }

        composable(Screen.AddItem.route) {

            AddItemScreen(

                onAddItem = {
                        name,
                        quantity,
                        category,
                        expirationDate ->

                    val currentUser =
                        userViewModel.currentUser.value
                            ?: return@AddItemScreen

                    pantryViewModel.addItem(

                        PantryItem(
                            userId = currentUser.id,
                            itemName = name,
                            quantity = quantity,
                            category = category,
                            expirationDate =
                                expirationDate
                        )
                    )

                    navController.popBackStack()
                }
            )
        }

        composable(
            Screen.EditItem.route
        ) {

            selectedItem?.let { item ->

                EditItemScreen(

                    item = item,

                    onSave = { updatedItem ->

                        pantryViewModel.updateItem(
                            updatedItem
                        )

                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            Screen.Dashboard.route
        ) {

            DashboardScreen(
                totalItems = totalItems,
                totalQuantity = totalQuantity,
                expiringSoon = expiringSoon,
                expiredItems = expiredItems,
                categories = categories,
                topCategory = topCategory
            )
        }
    }
}