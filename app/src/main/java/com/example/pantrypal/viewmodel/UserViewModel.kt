package com.example.pantrypal.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.pantrypal.repository.UserRepository

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    val loginSuccess = mutableStateOf(false)

    val registerSuccess = mutableStateOf(false)

    /** Firebase UID of the signed-in user, or null. */
    val currentUserId =
        mutableStateOf(repository.currentUserId)

    /** Last auth error to surface in the UI, or null. */
    val errorMessage =
        mutableStateOf<String?>(null)

    fun register(
        email: String,
        password: String
    ) {
        errorMessage.value = null

        repository.register(email, password) { success, error ->
            if (success) {
                registerSuccess.value = true
            } else {
                errorMessage.value =
                    error ?: "Registration failed"
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        errorMessage.value = null

        repository.login(email, password) { success, error ->
            if (success) {
                currentUserId.value = repository.currentUserId
                loginSuccess.value = true
            } else {
                loginSuccess.value = false
                errorMessage.value =
                    error ?: "Login failed"
            }
        }
    }

    fun logout() {
        repository.logout()
        currentUserId.value = null
        loginSuccess.value = false
    }

    fun resetRegisterSuccess() {
        registerSuccess.value = false
    }

    fun resetLoginSuccess() {
        loginSuccess.value = false
    }
}
