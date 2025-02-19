package com.jaguar.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaguar.littlelemon.components.Drawer
import com.jaguar.littlelemon.components.Header
import com.jaguar.littlelemon.helpers.dishDetailsPane
import com.jaguar.littlelemon.helpers.homeScreen
import com.jaguar.littlelemon.helpers.login
import com.jaguar.littlelemon.helpers.welcome
import com.jaguar.littlelemon.screens.DishDetails
import com.jaguar.littlelemon.screens.HomeScreen
import com.jaguar.littlelemon.screens.LoginPanel
import com.jaguar.littlelemon.screens.Welcome
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyNavigation()
        }
    }
}

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    LittleLemonTheme {
        Drawer(navController, drawerState) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { Header(drawerState, scope) },

                ) { innerPadding ->
                NavHost(navController = navController, startDestination = welcome.route) {
                    composable(welcome.route) {
                        Welcome(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(login.route) {
                        LoginPanel(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(homeScreen.route) {
                        HomeScreen(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
                    composable(dishDetailsPane.route) {
                        DishDetails(
                            dishImage = R.drawable.dish_1,
                            dishName = "Pasta",
                            dishDesc = "Pasta with white sauce",
                            Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}