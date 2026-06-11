package com.example.pantrypal.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.data.entity.User
import com.example.pantrypal.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val loginSuccess = mutableStateOf(false)

    fun register(user: User) {
        viewModelScope.launch {
            repository.register(user)
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {

            val user =
                repository.login(
                    email,
                    password
                )

            loginSuccess.value =
                user != null
        }
    }
}