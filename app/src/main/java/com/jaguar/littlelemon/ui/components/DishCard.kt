package com.jaguar.littlelemon.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Dish
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.MenuViewModel

private const val DESCRIPTION_MAX_LENGTH = 80

@Composable
fun DishCard(
    dish: Dish,
    menuViewModel: MenuViewModel,
    modifier: Modifier = Modifier,
    actions: Boolean = false
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(dish.getImageURL()).placeholder(R.drawable.image)
            .error(R.drawable.cross).build()
    )

    Card(modifier = modifier.padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = dish.getName(),
                    style = AppTypography.bodyLarge.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = if (dish.getDescription().length < DESCRIPTION_MAX_LENGTH) dish.getDescription()
                    else dish.getDescription().substring(0, DESCRIPTION_MAX_LENGTH) + "...",
                    style = AppTypography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "$${dish.getPrice()}",
                    style = AppTypography.bodyMedium,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                )

                if (actions) {
                    var showDeleteConfirmation: Boolean by remember { mutableStateOf(false) }
                    if (showDeleteConfirmation) {
                        ConfirmationDialog(
                            title = "Delete Dish",
                            message = "Are you sure want to remove the dish from the menu?\n${dish.getName()}",
                            onConfirm = {
                                menuViewModel.deleteDish(dish)
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
                                menuViewModel.updateDish(context, updatedDish)
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
                                menuViewModel.updateDish(context, updatedDish)
                                showCategoriesDialog = false
                                Toast.makeText(
                                    context,
                                    "Dish categories updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
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

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.dish_image_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }
    }

}
