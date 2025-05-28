package com.jaguar.littlelemon.models

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class User(
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var nonVeg: Boolean = false,
    var favorites: List<String> = emptyList()
) {

    fun updateData(
        firstName: String = this.firstName,
        lastName: String = this.lastName,
        email: String = this.email,
        nonVeg: Boolean = this.nonVeg,
        favorites: List<String> = this.favorites
    ): Task<Void> {
        val userMap = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "nonVeg" to nonVeg,
            "favorites" to favorites
        )
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        return FirebaseFirestore.getInstance().collection("users").document(currentUser!!.uid)
            .set(userMap)
    }

    fun getUser(uid: String) {
        FirebaseFirestore.getInstance().collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                try {
                    this.firstName = document.getString("firstName") ?: ""
                    this.lastName = document.getString("lastName") ?: ""
                    this.email = document.getString("email") ?: ""
                    this.nonVeg = document.getBoolean("nonVeg") ?: false
                    this.favorites = document.get("favorites") as? List<String> ?: emptyList()
                } catch (e: Exception) {
                    Log.e("Firestore", "Deserialization failed: ${e.localizedMessage}")
                }

            }.addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching User: ${exception.localizedMessage}")
            }
    }

    fun getCurrentUser() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser ?: throw Exception("User not logged in")
        getUser(currentUser.uid)
    }
}