package com.example.pantrypal.data.entity

import com.google.firebase.firestore.DocumentId

/**
 * A pantry item stored in Cloud Firestore (collection "pantryItems").
 *
 * All fields have defaults so Firestore can deserialize documents via
 * [com.google.firebase.firestore.DocumentSnapshot.toObject]. [itemId] is
 * annotated with [DocumentId]: on reads it is populated with the Firestore
 * document id, and on writes it is excluded from the stored data.
 */
data class PantryItem(

    @DocumentId
    val itemId: String = "",

    val userId: String = "",

    val itemName: String = "",

    val quantity: Int = 0,

    val category: String = "",

    val expirationDate: Long = 0L
)
