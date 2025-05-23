package com.jaguar.littlelemon.models

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val nonVeg: Boolean = false,
    val favorites: List<String> = emptyList()
)
