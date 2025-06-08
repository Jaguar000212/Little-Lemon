package com.jaguar.littlelemon.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jaguar.littlelemon.ui.components.Drawer
import com.jaguar.littlelemon.ui.components.Header
import com.jaguar.littlelemon.ui.screens.DishDetails
import com.jaguar.littlelemon.ui.screens.HomeScreen
import com.jaguar.littlelemon.ui.screens.LoginPanel
import com.jaguar.littlelemon.ui.screens.Profile
import com.jaguar.littlelemon.ui.screens.RegistrationPanel
import com.jaguar.littlelemon.ui.screens.Welcome
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme
import com.jaguar.littlelemon.viewModel.DishesViewModel
import com.jaguar.littlelemon.viewModel.UserViewModel

@Preview
@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val dishesViewModel: DishesViewModel = viewModel()
    val dishes by dishesViewModel.dishes.collectAsState()
    val userViewModel: UserViewModel = viewModel()
    val currentUser = userViewModel.user.collectAsState().value
    LittleLemonTheme {
        Drawer(navController, drawerState, userViewModel) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { Header(drawerState, scope) },

                ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = when {
                        currentUser == null -> WelcomeScreen.route
                        userViewModel.isProfileComplete() -> HomeScreen.route
                        else -> UserIncompleteProfileScreen.route
                    }
                ) {
                    composable(WelcomeScreen.route) {
                        Welcome(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(UserLoginScreen.route) {
                        LoginPanel(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(UserRegistrationScreen.route) {
                        RegistrationPanel(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(HomeScreen.route) {
                        HomeScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            viewModel = dishesViewModel
                        )
                    }
                    composable(
                        route = UserProfileScreen.route
                    ) {
                        Profile(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(
                        route = UserIncompleteProfileScreen.route
                    ) {
                        Profile(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel,
                            incomplete = true
                        )
                    }
                    composable(
                        route = DishDetailsScreen.route,
                        arguments = listOf(navArgument(DishDetailsScreen.ARG_DISH_NAME) {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val dishName =
                            backStackEntry.arguments?.getString(DishDetailsScreen.ARG_DISH_NAME)
                        val dish = dishes.find { it.getName() == dishName }
                        if (dish != null) {
                            DishDetails(
                                dish = dish, modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}