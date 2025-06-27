package com.jaguar.littlelemon.ui.screens.admin

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.ui.components.AdminManageCategoryDialog
import com.jaguar.littlelemon.ui.components.AdminManageDishDialog
import com.jaguar.littlelemon.ui.components.ConfirmationDialog
import com.jaguar.littlelemon.ui.components.Menu
import com.jaguar.littlelemon.viewModel.MenuViewModel


@Composable
fun AdminMenuScreen(
    modifier: Modifier = Modifier, navController: NavHostController, viewModel: MenuViewModel
) {
    val context = LocalContext.current
    var showAddDishDialog: Boolean by remember { mutableStateOf(false) }

    if (showAddDishDialog) {
        AdminManageDishDialog(
            onDismiss = { showAddDishDialog = false },
            onSave = { dish ->
                viewModel.addDish(context, dish)
                showAddDishDialog = false
                Toast.makeText(
                    context, "Dish added successfully", Toast.LENGTH_SHORT
                ).show()
            })
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Menu(navController, viewModel) { dish ->
                var showDeleteConfirmation: Boolean by remember { mutableStateOf(false) }
                if (showDeleteConfirmation) {
                    ConfirmationDialog(
                        title = "Delete Dish",
                        message = "Are you sure want to remove the dish from the menu?\n${dish.getName()}",
                        onConfirm = {
                            viewModel.deleteDish(dish)
                            showDeleteConfirmation = false
                            Toast.makeText(
                                context, "Dish deleted successfully", Toast.LENGTH_SHORT
                            ).show()
                        },
                        onDismiss = { showDeleteConfirmation = false })
                }
                var showEditDialog: Boolean by remember { mutableStateOf(false) }
                if (showEditDialog) {
                    AdminManageDishDialog(
                        dish = dish,
                        onDismiss = { showEditDialog = false },
                        onSave = { updatedDish ->
                            viewModel.updateDish(context, updatedDish)
                            showEditDialog = false
                            Toast.makeText(
                                context, "Dish updated successfully", Toast.LENGTH_SHORT
                            ).show()
                        })
                }

                var showCategoriesDialog: Boolean by remember { mutableStateOf(false) }
                if (showCategoriesDialog) {
                    AdminManageCategoryDialog(
                        dish = dish,
                        onDismiss = { showCategoriesDialog = false },
                        onSave = { updatedDish ->
                            viewModel.updateDish(context, updatedDish)
                            showCategoriesDialog = false
                            Toast.makeText(
                                context, "Dish categories updated successfully", Toast.LENGTH_SHORT
                            ).show()
                        })
                }
                Row {
                    IconButton(
                        {
                            showEditDialog = true
                        }, colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Yellow.copy(alpha = 0.1f),
                            contentColor = Color.Yellow,
                            disabledContentColor = colorResource(id = R.color.white),
                            disabledContainerColor = Color.Black,
                        )
                    ) {
                        Icon(
                            Icons.Outlined.Edit, contentDescription = "Edit Dish"
                        )
                    }

                    IconButton(
                        {
                            showCategoriesDialog = true
                        }, colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White.copy(alpha = 0.1f),
                            contentColor = Color.White,
                            disabledContentColor = colorResource(id = R.color.white),
                            disabledContainerColor = Color.Black
                        )
                    ) {
                        Icon(
                            Icons.Outlined.Info, contentDescription = "Manage Categories"
                        )
                    }

                    IconButton(
                        {
                            showDeleteConfirmation = true
                        }, colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Red.copy(alpha = 0.1f),
                            contentColor = Color.Red,
                            disabledContentColor = colorResource(id = R.color.white),
                            disabledContainerColor = Color.Black
                        )
                    ) {
                        Icon(
                            Icons.Outlined.Delete, contentDescription = "Delete Dish"
                        )
                    }
                }

            }
        }

        FloatingActionButton(
            onClick = {
                showAddDishDialog = true
            }, modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp) // spacing from edges
        ) {
            Icon(Icons.Outlined.Add, contentDescription = "Add Menu Item")
        }
    }
}
