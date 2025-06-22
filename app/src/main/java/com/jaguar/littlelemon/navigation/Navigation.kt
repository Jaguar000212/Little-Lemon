package com.jaguar.littlelemon.navigation

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.jaguar.littlelemon.ui.screens.DishDetailsScreen
import com.jaguar.littlelemon.ui.screens.Welcome
import com.jaguar.littlelemon.ui.screens.admin.AdminDishesScreen
import com.jaguar.littlelemon.ui.screens.admin.AdminHomeScreen
import com.jaguar.littlelemon.ui.screens.admin.AdminLoginScreen
import com.jaguar.littlelemon.ui.screens.user.UserHomeScreen
import com.jaguar.littlelemon.ui.screens.user.UserLoginScreen
import com.jaguar.littlelemon.ui.screens.user.UserProfileScreen
import com.jaguar.littlelemon.ui.screens.user.UserRegistrationScreen
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme
import com.jaguar.littlelemon.viewModel.DishesViewModel
import com.jaguar.littlelemon.viewModel.UserViewModel


@Composable
fun MyNavigation() {
    val context = LocalContext.current
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
                    navController = navController, startDestination = when {
                        currentUser == null -> WelcomeScreen.route
                        userViewModel.checkIfAdmin() -> AdminHomeScreen.route
                        userViewModel.isProfileComplete() -> UserHomeScreen.route
                        else -> UserIncompleteProfileScreen.route
                    }
                ) {
                    composable(WelcomeScreen.route) {
                        Welcome(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(UserLoginScreen.route) {
                        UserLoginScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(UserRegistrationScreen.route) {
                        UserRegistrationScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(UserHomeScreen.route) {
                        UserHomeScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            viewModel = dishesViewModel
                        )
                    }
                    composable(
                        route = UserProfileScreen.route
                    ) {
                        UserProfileScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(
                        route = UserIncompleteProfileScreen.route
                    ) {
                        Toast.makeText(context, "Please complete your profile", Toast.LENGTH_SHORT)
                            .show()
                        UserProfileScreen(
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
                            DishDetailsScreen(
                                dish = dish, modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                    composable(
                        route = AdminLoginScreen.route
                    ) {
                        AdminLoginScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel
                        )
                    }
                    composable(
                        route = AdminHomeScreen.route
                    ) {
                        AdminHomeScreen(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(
                        route = AdminDishesScreen.route
                    ) {
                        AdminDishesScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            viewModel = dishesViewModel
                        )
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