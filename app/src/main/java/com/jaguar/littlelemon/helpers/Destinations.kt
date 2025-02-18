package com.jaguar.littlelemon.helpers

interface Destinations {
    val route: String
}

object welcome : Destinations {
    override val route = "Welcome"
}

object homeScreen : Destinations {
    override val route = "HomeScreen"
}

object login : Destinations {
    override val route = "Login"
}

object dishDetailsPane : Destinations {
    override val route = "DishDetailsPane"
}