package com.jaguar.littlelemon.models

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

    fun clearUserData() {
        name = ""
        email = ""
        phone = ""
        nonVeg = false
        favorites = emptyList()
    }
}
