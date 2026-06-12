package com.example.pantrypal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pantrypal.data.entity.PantryItem
import com.example.pantrypal.repository.PantryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PantryViewModel(
    private val repository: PantryRepository
) : ViewModel() {

    private val _items =
        MutableStateFlow<List<PantryItem>>(emptyList())

    val items =
        _items.asStateFlow()

    private var loadJob: Job? = null

    fun addItem(item: PantryItem) {
        repository.insert(item)
    }

    fun updateItem(item: PantryItem) {
        repository.update(item)
    }

    fun deleteItem(item: PantryItem) {
        repository.delete(item)
    }

    fun loadItems(userId: String) {
        // Cancel any previous listener before starting a new one.
        loadJob?.cancel()

        loadJob = viewModelScope.launch {
            repository
                .getItems(userId)
                .collect { _items.value = it }
        }
    }
}
