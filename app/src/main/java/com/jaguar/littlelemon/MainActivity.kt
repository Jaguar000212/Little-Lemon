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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.jaguar.littlelemon.components.Drawer
import com.jaguar.littlelemon.components.Header
import com.jaguar.littlelemon.helpers.HomeScreen
import com.jaguar.littlelemon.helpers.Login
import com.jaguar.littlelemon.helpers.Welcome
import com.jaguar.littlelemon.screens.DishDetails
import com.jaguar.littlelemon.screens.HomeScreen
import com.jaguar.littlelemon.screens.LoginPanel
import com.jaguar.littlelemon.screens.Welcome
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme
import com.jaguar.littlelemon.viewModel.DishesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            MyNavigation()
        }
    }
}

@Preview
@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val viewModel = DishesViewModel()
    val dishes by viewModel.dishes.collectAsState()
    LittleLemonTheme {
        Drawer(navController, drawerState) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { Header(drawerState, scope) },

                ) { innerPadding ->
                NavHost(navController = navController, startDestination = Welcome.route) {
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
                    composable(HomeScreen.route) {
                        HomeScreen(
                            Modifier.padding(innerPadding), navController = navController
                        )
                    }
//                    if (dishes.isNotEmpty()) {
//                        dishes.forEach { dish ->
//                            val route = "DishDetailsPane/${dish.name}/${dish.name}"
//                            composable(route) {
//                                DishDetails(
//                                    dish = dish,
//                                    modifier = Modifier.padding(innerPadding),
//                                    navController = navController
//                                )
//                            }
//                        }
//                    }
                    /* TODO : Implement Details Page for dishes */
                }
            }
        }
    }
}