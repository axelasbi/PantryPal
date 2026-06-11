package com.example.pantrypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.data.entity.PantryItem
import com.example.pantrypal.repository.PantryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PantryViewModel(
    private val repository: PantryRepository
) : ViewModel() {

    val items =
        repository
            .getItems(1)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    fun addItem(
        item: PantryItem
    ) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun deleteItem(
        item: PantryItem
    ) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }
}