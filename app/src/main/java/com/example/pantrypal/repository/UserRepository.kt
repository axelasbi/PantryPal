package com.example.pantrypal.repository

import com.google.firebase.auth.FirebaseAuth

/**
 * Authentication backed by Firebase Auth (email / password).
 *
 * Replaces the previous Room-based user table. Firebase securely hashes and
 * stores credentials server-side, so passwords are never persisted by the app.
 */
class UserRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    /** The signed-in user's unique id, or null if no one is signed in. */
    val currentUserId: String?
        get() = auth.currentUser?.uid

    fun register(
        email: String,
        password: String,
        onResult: (success: Boolean, error: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun login(
        email: String,
        password: String,
        onResult: (success: Boolean, error: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun logout() {
        auth.signOut()
    }
}
