package com.jaguar.littlelemon.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.ui.components.Menu
import com.jaguar.littlelemon.viewModel.MenuViewModel

@Composable
fun AdminMenuScreen(
    modifier: Modifier, navController: NavHostController, viewModel: MenuViewModel
) {
    Column(modifier = modifier.fillMaxSize()) {
        Menu(navController, viewModel, actions = true)
    }
}