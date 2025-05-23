package com.jaguar.littlelemon.helpers

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

object Profile : Destinations {
    override val route = "Profile"
}

object DishDetailsPane : Destinations {
    const val ARG_DISH_NAME = "dishName"
    override val route = "DishDetailsPane/{$ARG_DISH_NAME}"

    fun createRoute(dishName: String): String {
        return "DishDetailsPane/$dishName"
    }
}
