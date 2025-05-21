package com.jaguar.littlelemon.helpers

import com.jaguar.littlelemon.models.Dish

interface Destinations {
    val route: String
}

object Welcome : Destinations {
    override val route = "Welcome"
}

object HomeScreen : Destinations {
    override val route = "HomeScreen"
}

object Login : Destinations {
    override val route = "Login"
}

class DishDetailsPane(dish: Dish) : Destinations {
    override val route = "DishDetailsPane/${dish.name}"
}