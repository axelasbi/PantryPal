package com.example.pantrypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pantrypal.repository.PantryRepository

class PantryViewModelFactory(
    private val repository: PantryRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                PantryViewModel::class.java
            )
        ) {
            @Suppress("UNCHECKED_CAST")
            return PantryViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel"
        )
    }
}