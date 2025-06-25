package com.jaguar.littlelemon.models

data class Dish(
    private val id: String = "",
    private val name: String = "",
    private val description: String = "",
    private val ingredients: List<String> = emptyList(),
    private val categories: List<String> = emptyList(),
    private val calories: Int = 0,
    private val imageURL: String = "",
    private val nonVeg: Boolean = false,
    private val price: Double = 0.0,
    private val type: String = "cuisine"
) {
    fun getId(): String = id
    fun getName(): String = name
    fun getDescription(): String = description
    fun getIngredients(): List<String> = ingredients
    fun getCategories(): List<String> = categories
    fun getCalories(): Int = calories
    fun getImageURL(): String = imageURL
    fun isNonVeg(): Boolean = nonVeg
    fun getPrice(): Double = price


    fun getFormattedIngredients(): String {
        return if (ingredients.isNotEmpty()) ingredients.mapIndexed { index, ingredient ->
            "${index + 1}. ${ingredient.replaceFirstChar { char -> char.uppercase() }}"
        }.joinToString("\n") else "Ingredients not available"
    }
}
