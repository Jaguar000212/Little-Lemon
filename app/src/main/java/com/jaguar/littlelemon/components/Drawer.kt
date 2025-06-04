package com.jaguar.littlelemon.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.exceptions.UserNotLoggedInException
import com.jaguar.littlelemon.models.checkIfLoggedIn
import com.jaguar.littlelemon.navigation.HomeScreen
import com.jaguar.littlelemon.navigation.Profile
import com.jaguar.littlelemon.navigation.Welcome

@Composable
fun NavigationIcon(icon: ImageVector, label: String) {
    Icon(
        icon,
        contentDescription = label,
        modifier = Modifier.padding(horizontal = 20.dp),
        tint = colorResource(
            id = R.color.yellow
        )
    )
}

@Composable
fun Drawer(navController: NavHostController, state: DrawerState, content: @Composable () -> Unit) {
    val context = LocalContext.current
    ModalNavigationDrawer(drawerState = state, drawerContent = {
        ModalDrawerSheet {
            Text("Little Lemon Restaurant", modifier = Modifier.padding(16.dp))
            HorizontalDivider()

            NavigationDrawerItem(
                label = {
                    Text("Home")
                },
                selected = false,
                icon = { NavigationIcon(Icons.Outlined.Home, "Home") },
                onClick = {
                    try {
                        checkIfLoggedIn()
                        navController.navigate(HomeScreen.route) {
                            popUpTo(Welcome.route) { inclusive = true }
                        }
                    } catch (e: UserNotLoggedInException) {
                        Toast.makeText(
                            context, "Please login first", Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            NavigationDrawerItem(
                label = {
                    Text("Reserve a Table")
                },
                icon = { NavigationIcon(Icons.Outlined.DateRange, "Reserve a Table") },
                selected = false,
                onClick = { /*TODO*/ })

            HorizontalDivider()

            NavigationDrawerItem(
                label = {
                    Text("Profile")
                },
                selected = false,
                icon = { NavigationIcon(Icons.Outlined.AccountCircle, "Profile") },
                onClick = {
                    try {
                        checkIfLoggedIn()
                        navController.navigate(Profile.route) {
                            popUpTo(Welcome.route) { inclusive = true }
                        }
                    } catch (e: UserNotLoggedInException) {
                        Toast.makeText(
                            context, "Please login first", Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            NavigationDrawerItem(
                label = {
                    Text("Sign Out")
                },
                selected = false,
                icon = { NavigationIcon(Icons.AutoMirrored.Outlined.ArrowBack, "Sign Out") },
                onClick = {
                    val auth = FirebaseAuth.getInstance()
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        auth.signOut()
                        navController.navigate(Welcome.route) {
                            popUpTo(HomeScreen.route) { inclusive = true }
                        }
                    } else Toast.makeText(
                        context, "You are not logged in", Toast.LENGTH_SHORT
                    ).show()
                })
            HorizontalDivider()
        }
    }) {
        content()
    }
}