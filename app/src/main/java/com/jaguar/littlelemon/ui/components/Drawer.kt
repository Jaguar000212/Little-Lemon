package com.jaguar.littlelemon.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException
import com.jaguar.littlelemon.navigation.HomeScreen
import com.jaguar.littlelemon.navigation.UserProfileScreen
import com.jaguar.littlelemon.navigation.WelcomeScreen
import com.jaguar.littlelemon.navigation.currentRoute
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun NavigationIcon(icon: ImageVector, label: String) {
    Icon(
        icon,
        contentDescription = label,
        modifier = Modifier.padding(horizontal = 18.dp),
        tint = colorResource(
            id = R.color.yellow
        )
    )
}

@Composable
fun Drawer(
    navController: NavHostController,
    state: DrawerState,
    userViewModel: UserViewModel,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentRoute = currentRoute(navController)
    ModalNavigationDrawer(drawerState = state, drawerContent = {
        ModalDrawerSheet {
            Text(
                stringResource(R.string.little_lemon_restaurant),
                style = AppTypography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )
            HorizontalDivider()

            NavigationDrawerItem(
                label = {
                    Text("Home", style = AppTypography.bodyLarge)
                },
                selected = currentRoute == HomeScreen.route,
                icon = { NavigationIcon(Icons.Outlined.Home, "Home") },
                onClick = {
                    scope.launch {
                        state.close()
                    }
                    try {
                        userViewModel.checkIfLoggedIn()
                        navController.navigate(HomeScreen.route) {
                            popUpTo(HomeScreen.route) { inclusive = false }
                        }
                    } catch (e: UserNotLoggedInException) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.login_error_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            NavigationDrawerItem(
                label = {
                    Text("Reserve a Table", style = AppTypography.bodyLarge)
                },
                icon = { NavigationIcon(Icons.Outlined.DateRange, "Reserve a Table") },
                selected = false, //TODO
                onClick = { /*TODO*/
                    scope.launch {
                        state.close()
                    }
                })

            HorizontalDivider()

            NavigationDrawerItem(
                label = {
                    Text("Profile", style = AppTypography.bodyLarge)
                },
                selected = currentRoute == UserProfileScreen.route,
                icon = { NavigationIcon(Icons.Outlined.AccountCircle, "Profile") },
                onClick = {
                    scope.launch {
                        state.close()
                    }
                    try {
                        userViewModel.checkIfLoggedIn()
                        navController.navigate(UserProfileScreen.route) {
                            popUpTo(HomeScreen.route) { inclusive = false }
                        }
                    } catch (e: UserNotLoggedInException) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.login_error_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            NavigationDrawerItem(
                label = {
                    Text("Sign Out", style = AppTypography.bodyLarge)
                },
                selected = false,
                icon = { NavigationIcon(Icons.AutoMirrored.Outlined.ArrowBack, "Sign Out") },
                onClick = {
                    scope.launch {
                        state.close()
                    }
                    try {
                        userViewModel.checkIfLoggedIn()
                        userViewModel.logOut()
                        Toast.makeText(
                            context,
                            context.getString(R.string.logging_out_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(WelcomeScreen.route) {
                            popUpTo(WelcomeScreen.route) { inclusive = true }
                        }
                    } catch (e: UserNotLoggedInException) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.login_error_toast),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            HorizontalDivider()
        }
    }) {
        content()
    }
}