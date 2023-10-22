package com.example.challengeempat.viewmodelregister

import com.example.challengeempat.modeluser.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()



    fun saveUserDataToFirestore(
        username: String,
        noTelepon: String,
        email: String,

    ) {
        // Asumsikan Anda memiliki koleksi "users" di Firestore
        val user = hashMapOf(
            "username" to username,
            "noTelepon" to noTelepon,
            "email" to email
        )

        firestore.collection("users")
            .document(email)
            .set(user)
            .addOnSuccessListener {
                // Penyimpanan ke Firestore berhasil
            }
            .addOnFailureListener { e ->
                // Penyimpanan ke Firestore gagal, Anda dapat menangani kesalahan di sini
            }
    }

    suspend fun getUserDocument(email: String): User? {
        val userCollection = firestore.collection("users")
        return try {
            val documentSnapshot = userCollection.document(email).get().await()
            if (documentSnapshot.exists()) {
                val userData = documentSnapshot.toObject(User::class.java)
                userData
            } else {
                null
            }
        } catch (e: Exception) {
            // Handle any exceptions that may occur during the data retrieval
            null
        }
    }
}
