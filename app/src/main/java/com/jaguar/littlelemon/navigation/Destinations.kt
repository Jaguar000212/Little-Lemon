package com.jaguar.littlelemon.navigation

interface Destinations {
    val route: String
}

object WelcomeScreen : Destinations {
    override val route = "WelcomeScreen"
}

object HomeScreen : Destinations {
    override val route = "HomeScreen"
}

object UserLoginScreen : Destinations {
    override val route = "UserLoginScreen"
}

object UserRegistrationScreen : Destinations {
    override val route = "UserRegistrationScreen"
}

object UserProfileScreen : Destinations {
    override val route = "UserProfileScreen"
}

object UserIncompleteProfileScreen : Destinations {
    override val route = "UserIncompleteProfileScreen"
}

object DishDetailsScreen : Destinations {
    const val ARG_DISH_NAME = "dishName"
    override val route = "DishDetailsScreen/{$ARG_DISH_NAME}"

    fun createRoute(dishName: String): String {
        return "DishDetailsScreen/$dishName"
    }
}
