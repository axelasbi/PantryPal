package com.example.pantrypal.repository

import com.example.pantrypal.data.dao.PantryDao
import com.example.pantrypal.data.entity.PantryItem
import kotlinx.coroutines.flow.Flow

class PantryRepository(
    private val pantryDao: PantryDao
) {

    suspend fun insert(
        item: PantryItem
    ) {
        pantryDao.insert(item)
    }

    suspend fun update(
        item: PantryItem
    ) {
        pantryDao.update(item)
    }

    suspend fun delete(
        item: PantryItem
    ) {
        pantryDao.delete(item)
    }

    fun getItems(
        userId: Int
    ): Flow<List<PantryItem>> {
        return pantryDao.getItems(userId)
    }
}