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
import com.jaguar.littlelemon.components.Drawer
import com.jaguar.littlelemon.components.Header
import com.jaguar.littlelemon.screens.DataCollectionPanel
import com.jaguar.littlelemon.screens.DishDetails
import com.jaguar.littlelemon.screens.HomeScreen
import com.jaguar.littlelemon.screens.LoginPanel
import com.jaguar.littlelemon.screens.Profile
import com.jaguar.littlelemon.screens.RegistrationPanel
import com.jaguar.littlelemon.screens.Welcome
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme
import com.jaguar.littlelemon.viewModel.DishesViewModel

@Preview
@Composable
fun MyNavigation(currentUser: Boolean = false) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel: DishesViewModel = viewModel()
    val dishes by viewModel.dishes.collectAsState()
    LittleLemonTheme {
        Drawer(navController, drawerState) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { Header(drawerState, scope) },

                ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = if (currentUser) HomeScreen.route else Welcome.route
                ) {
                    composable(Welcome.route) {
                        Welcome(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(Login.route) {
                        LoginPanel(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(Registration.route) {
                        RegistrationPanel(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(DataCollection.route) {
                        DataCollectionPanel(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(HomeScreen.route) {
                        HomeScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(
                        route = Profile.route,
                    ) {
                        Profile(
                            Modifier.padding(innerPadding)
                        )
                    }
                    composable(
                        route = DishDetailsPane.route,
                        arguments = listOf(navArgument(DishDetailsPane.ARG_DISH_NAME) {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val dishName =
                            backStackEntry.arguments?.getString(DishDetailsPane.ARG_DISH_NAME)
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