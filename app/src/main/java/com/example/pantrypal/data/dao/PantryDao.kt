package com.example.pantrypal.data.dao

import androidx.room.*
import com.example.pantrypal.data.entity.PantryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryDao {

    @Insert
    suspend fun insert(item: PantryItem)

    @Update
    suspend fun update(item: PantryItem)

    @Delete
    suspend fun delete(item: PantryItem)

    @Query("""
        SELECT * 
        FROM pantry_items
        WHERE userId = :userId
    """)
    fun getItems(userId: Int): Flow<List<PantryItem>>
}