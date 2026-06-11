package com.example.pantrypal.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pantry_items")
data class PantryItem(

    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,

    val userId: Int,

    val itemName: String,

    val quantity: Int,

    val category: String,

    val expirationDate: Long
)