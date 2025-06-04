package com.jaguar.littlelemon.models

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException

data class User(
    private var name: String = "",
    private var email: String = "",
    private var phone: String = "",
    private var nonVeg: Boolean = false,
    private var favorites: List<String> = emptyList()
) {

    fun getName(): String = name
    fun getEmail(): String = email
    fun getPhone(): String = phone
    fun isNonVeg(): Boolean = nonVeg
    fun getFavorites(): List<String> = favorites

    fun updateData(
        name: String = this.name,
        email: String = this.email,
        phone: String = this.phone,
        nonVeg: Boolean = this.nonVeg,
        favorites: List<String> = this.favorites
    ): Task<Void> {
        val userMap = hashMapOf(
            "name" to name,
            "email" to email,
            "phone" to phone,
            "nonVeg" to nonVeg,
            "favorites" to favorites
        )
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser ?: return Tasks.forException(UserNotLoggedInException())

        return FirebaseFirestore.getInstance().collection("users").document(currentUser.uid)
            .set(userMap)
    }

    fun reset() {
        name = ""
        email = ""
        phone = ""
        nonVeg = false
        favorites = emptyList()
    }
}

fun checkIfLoggedIn() {
    val auth = FirebaseAuth.getInstance()
    if (auth.currentUser == null) throw UserNotLoggedInException("User not logged in or data fetch failed.")
}