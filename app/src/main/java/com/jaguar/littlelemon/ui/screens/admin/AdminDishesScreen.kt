package com.jaguar.littlelemon.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.ui.components.DishesList
import com.jaguar.littlelemon.viewModel.DishesViewModel

@Composable
fun AdminDishesScreen(
    modifier: Modifier, navController: NavHostController, viewModel: DishesViewModel
) {
    Column(modifier = modifier.fillMaxSize()) {
        DishesList(navController, viewModel)
    }
}