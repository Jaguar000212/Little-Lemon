package com.jaguar.littlelemon.navigation

interface Destinations {
    val route: String
}

object WelcomeScreen : Destinations {
    override val route = "WelcomeScreen"
}

object UserHomeScreen : Destinations {
    override val route = "UserHomeScreen"
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
    const val ARG_DISH_ID = "dishName"
    override val route = "DishDetailsScreen/{$ARG_DISH_ID}"

    fun createRoute(dishID: String): String {
        return "DishDetailsScreen/$dishID"
    }
}

object AdminLoginScreen : Destinations {
    override val route = "AdminLoginScreen"
}

object AdminHomeScreen : Destinations {
    override val route = "AdminHomeScreen"
}

object AdminMenuScreen : Destinations {
    override val route = "AdminMenuScreen"
}
