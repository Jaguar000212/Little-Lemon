package com.jaguar.littlelemon.navigation

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

object Registration : Destinations {
    override val route = "Registration"
}

object DataCollection : Destinations {
    override val route = "DataCollection"
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
