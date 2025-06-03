package com.jaguar.littlelemon.models

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException

data class User(
    private var firstName: String = "",
    private var lastName: String = "",
    private var email: String = "",
    private var nonVeg: Boolean = false,
    private var favorites: List<String> = emptyList()
) {

    fun getFirstName(): String = firstName
    fun getLastName(): String = lastName
    fun getEmail(): String = email
    fun isNonVeg(): Boolean = nonVeg
    fun getFavorites(): List<String> = favorites

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
        val currentUser = auth.currentUser ?: return Tasks.forException(UserNotLoggedInException())

        return FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)
            .set(userMap)
    }

    fun reset() {
        firstName = ""
        lastName = ""
        email = ""
        nonVeg = false
        favorites = emptyList()
    }
}


fun checkIfLoggedIn(): Boolean {
    val auth = FirebaseAuth.getInstance()
    return auth.currentUser != null
}

fun LoginException() {
    Log.e("User", "User not logged in or data fetch failed.")
    if (!checkIfLoggedIn()) throw UserNotLoggedInException("User not logged in or data fetch failed.")
}