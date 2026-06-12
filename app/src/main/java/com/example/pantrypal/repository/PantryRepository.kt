package com.example.pantrypal.repository

import com.example.pantrypal.data.entity.PantryItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Pantry items backed by Cloud Firestore (collection "pantryItems").
 *
 * Replaces the previous Room DAO. Each item document carries a [PantryItem.userId]
 * field so a user only ever sees their own items. Reads use a real-time snapshot
 * listener, so changes (including from other devices) stream in automatically.
 */
class PantryRepository(
    firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    private val collection = firestore.collection("pantryItems")

    /** Emits the user's items and re-emits whenever they change in Firestore. */
    fun getItems(userId: String): Flow<List<PantryItem>> = callbackFlow {
        val registration = collection
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val items = snapshot
                    ?.documents
                    ?.mapNotNull { it.toObject(PantryItem::class.java) }
                    ?: emptyList()

                trySend(items)
            }

        awaitClose { registration.remove() }
    }

    fun insert(item: PantryItem) {
        // itemId is @DocumentId, so Firestore assigns it on add().
        collection.add(item)
    }

    fun update(item: PantryItem) {
        if (item.itemId.isNotEmpty()) {
            collection.document(item.itemId).set(item)
        }
    }

    fun delete(item: PantryItem) {
        if (item.itemId.isNotEmpty()) {
            collection.document(item.itemId).delete()
        }
    }
}
