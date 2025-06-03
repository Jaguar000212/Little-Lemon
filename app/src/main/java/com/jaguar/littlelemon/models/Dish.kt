package com.jaguar.littlelemon.models

data class Dish(
    private val name: String = "",
    private val description: String = "",
    private val imageURL: String = "",
    private val price: Double = 0.0
) {
    fun getName(): String = name
    fun getDescription(): String = description
    fun getImageURL(): String = imageURL
    fun getPrice(): Double = price
}
