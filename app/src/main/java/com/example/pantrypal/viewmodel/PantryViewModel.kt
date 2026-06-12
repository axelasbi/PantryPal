package com.example.pantrypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.data.entity.PantryItem
import com.example.pantrypal.repository.PantryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PantryViewModel(
    private val repository: PantryRepository
) : ViewModel() {

    private var currentUserId = 0

    private val _items =
        MutableStateFlow<List<PantryItem>>(emptyList())

    val items =
        _items.asStateFlow()

    fun addItem(
        item: PantryItem
    ) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun updateItem(
        item: PantryItem
    ) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun deleteItem(
        item: PantryItem
    ) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun loadItems(
        userId: Int
    ) {

        viewModelScope.launch {

            repository
                .getItems(userId)
                .collect {

                    _items.value = it
                }
        }
    }
}