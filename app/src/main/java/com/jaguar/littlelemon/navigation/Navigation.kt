package com.jaguar.littlelemon.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Configs
import com.jaguar.littlelemon.ui.components.Drawer
import com.jaguar.littlelemon.ui.components.Header
import com.jaguar.littlelemon.ui.screens.DishDetailsScreen
import com.jaguar.littlelemon.ui.screens.Welcome
import com.jaguar.littlelemon.ui.screens.admin.AdminHomeScreen
import com.jaguar.littlelemon.ui.screens.admin.AdminLoginScreen
import com.jaguar.littlelemon.ui.screens.admin.AdminMenuScreen
import com.jaguar.littlelemon.ui.screens.user.UserFavouritesScreen
import com.jaguar.littlelemon.ui.screens.user.UserHomeScreen
import com.jaguar.littlelemon.ui.screens.user.UserLoginScreen
import com.jaguar.littlelemon.ui.screens.user.UserMenuScreen
import com.jaguar.littlelemon.ui.screens.user.UserProfileScreen
import com.jaguar.littlelemon.ui.screens.user.UserRegistrationScreen
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme
import com.jaguar.littlelemon.viewModel.MenuViewModel
import com.jaguar.littlelemon.viewModel.UserViewModel


@Composable
fun MyNavigation() {
    LaunchedEffect(Unit) {
        Configs.initConfigs()
    }
    val isReady by Configs.isReady.collectAsState()
    if (!isReady) {
        LittleLemonTheme {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        return
    }
    val context = LocalContext.current
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuViewModel: MenuViewModel = viewModel()
    val dishes by menuViewModel.dishes.collectAsState()
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
                            viewModel = menuViewModel
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
                        Toast.makeText(context,
                            stringResource(R.string.toast_incomplete_profile), Toast.LENGTH_SHORT)
                            .show()
                        UserProfileScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            userViewModel = userViewModel,
                            incomplete = true
                        )
                    }
                    composable(
                        route = UserMenuScreen.route
                    ) {
                        UserMenuScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            menuViewModel = menuViewModel,
                            userViewModel = userViewModel,
                        )
                    }
                    composable(
                        route = UserFavouritesScreen.route
                    ) {
                        UserFavouritesScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            menuViewModel = menuViewModel,
                            userViewModel = userViewModel
                        )
                    }
                    composable(
                        route = DishDetailsScreen.route,
                        arguments = listOf(navArgument(DishDetailsScreen.ARG_DISH_ID) {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val dishID =
                            backStackEntry.arguments?.getString(DishDetailsScreen.ARG_DISH_ID)
                        val dish = dishes.find { it.getId() == dishID }
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
                        route = AdminMenuScreen.route
                    ) {
                        AdminMenuScreen(
                            Modifier.padding(innerPadding),
                            navController = navController,
                            viewModel = menuViewModel
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