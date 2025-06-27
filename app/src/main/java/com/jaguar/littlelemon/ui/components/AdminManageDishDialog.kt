package com.jaguar.littlelemon.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Configs
import com.jaguar.littlelemon.models.Dish

@Composable
fun AdminManageDishDialog(
    dish: Dish = Dish(), onDismiss: () -> Unit, onSave: (Dish) -> Unit
) {
    val types: List<String> by Configs.types.collectAsState()
    var name: String by remember { mutableStateOf(dish.getName()) }
    var description: String by remember { mutableStateOf(dish.getDescription()) }
    var price: Double by remember { mutableDoubleStateOf(dish.getPrice()) }
    var calories: Int by remember { mutableIntStateOf(dish.getCalories()) }
    var imageURL: Uri? by remember { mutableStateOf(dish.getImageURL().toUri()) }
    var ingredients: Set<String> by remember { mutableStateOf(dish.getIngredients().toSet()) }
    var nonVeg: Boolean by remember { mutableStateOf(dish.isNonVeg()) }
    var topPick: Boolean by remember { mutableStateOf(dish.isTopPick()) }
    var type: String by remember { mutableStateOf(dish.getType()) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageURL = it
    }

    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(imageURL).placeholder(R.drawable.image)
            .error(R.drawable.cross).build()
    )

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(stringResource(R.string.edit_dish)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.name)) },
                    singleLine = true
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    maxLines = 3,
                    label = { Text(stringResource(R.string.ddescription)) })
                OutlinedTextField(
                    value = price.toString(),
                    onValueChange = { price = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text(stringResource(R.string.price)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = calories.toString(),
                    onValueChange = { calories = it.toIntOrNull() ?: 0 },
                    label = { Text(stringResource(R.string.calories)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
                OutlinedTextField(
                    value = ingredients.joinToString("\n"),
                    onValueChange = { it ->
                        ingredients = it.split("\n").map { it.trim() }.toSet()
                    },
                    maxLines = 3,
                    label = { Text(stringResource(R.string.ingredients)) })
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = nonVeg, onCheckedChange = { nonVeg = it })
                    Text(stringResource(R.string.non_veg))
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = topPick, onCheckedChange = { topPick = it })
                    Text(stringResource(R.string.top_pick))
                }
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    types.forEach { t ->
                        FilterChip(selected = t == type, onClick = {
                            type = t
                        }, label = {
                            Text(t)
                        })
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        {
                            launcher.launch("image/*")
                        }, contentPadding = PaddingValues(8.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Edit,
                            contentDescription = stringResource(R.string.edit_image),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Text(
                            stringResource(R.string.upload_image),
                            modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)
                        )
                    }
                    Image(
                        painter = painter,
                        contentDescription = stringResource(R.string.dish_image_desc),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(50.dp)
                            .clip(RoundedCornerShape(25.dp))
                    )
                }
            }
        },
        confirmButton = {
            OutlinedButton(onClick = {
                val updatedDish = dish.copy(
                    name = name,
                    description = description,
                    price = price,
                    calories = calories,
                    imageURL = imageURL.toString(),
                    nonVeg = nonVeg,
                    ingredients = ingredients.toList(),
                    type = type,
                    topPick = topPick
                )
                onSave(updatedDish)
            }) {
                Text(stringResource(R.string.save))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        })
}
