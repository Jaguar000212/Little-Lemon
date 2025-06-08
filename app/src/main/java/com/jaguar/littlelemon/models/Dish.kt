package com.jaguar.littlelemon.models

data class Dish(
    private val name: String = "",
    private val description: String = "",
    private val imageURL: String = "",
    private val price: Double = 0.0,
    private val isNonVeg: Boolean = false,
    private val calories: Int = 0,
    private val ingredients: List<String> = emptyList()
) {
    fun getName(): String = name
    fun getDescription(): String = description
    fun getImageURL(): String = imageURL
    fun getPrice(): Double = price
    fun isNonVeg(): Boolean = isNonVeg
    fun getCalories(): Int = calories
    fun getIngredients(): List<String> = ingredients
    fun getFormattedIngredients(): String {
        return if (ingredients.isNotEmpty()) ingredients.mapIndexed { index, ingredient ->
            "${index + 1}. ${ingredient.replaceFirstChar { char -> char.uppercase() }}"
        }.joinToString("\n") else "Ingredients not available"
    }
}
