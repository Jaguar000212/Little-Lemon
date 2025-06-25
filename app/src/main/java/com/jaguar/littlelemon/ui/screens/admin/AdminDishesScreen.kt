package com.jaguar.littlelemon.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.ui.components.ConfirmationDialog
import com.jaguar.littlelemon.ui.components.Menu
import com.jaguar.littlelemon.viewModel.MenuViewModel

@Composable
fun AdminDishesScreen(
    modifier: Modifier, navController: NavHostController, viewModel: MenuViewModel
) {
    Column(modifier = modifier.fillMaxSize()) {
        Menu(navController, viewModel, actions = true)
    }
}