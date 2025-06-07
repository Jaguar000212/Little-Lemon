package com.jaguar.littlelemon.models

import com.google.firebase.auth.FirebaseAuth
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException

data class User(
    private var name: String = "",
    private var email: String = "",
    private var phone: String = "",
    private var nonVeg: Boolean = false,
    private var favorites: List<String> = emptyList()
) {

    companion object {
        fun checkIfLoggedIn() {
            val auth = FirebaseAuth.getInstance()
            if (auth.currentUser == null) throw UserNotLoggedInException("User not logged in or data fetch failed.")
        }
    }

    fun getName(): String = name
    fun getEmail(): String = email
    fun getPhone(): String = phone
    fun isNonVeg(): Boolean = nonVeg
    fun getFavorites(): List<String> = favorites

    fun reset() {
        name = ""
        email = ""
        phone = ""
        nonVeg = false
        favorites = emptyList()
    }
}
