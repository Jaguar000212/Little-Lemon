package com.jaguar.littlelemon.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Configs
import com.jaguar.littlelemon.models.Dish
import com.jaguar.littlelemon.ui.theme.AppTypography


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AdminManageCategoryDialog(
    dish: Dish,
    onSave: (Dish) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedCategories: Set<String> by remember { mutableStateOf(dish.getCategories().toSet()) }
    val categories by Configs.categories.collectAsState()

    AlertDialog(onDismissRequest = { onDismiss() }, title = {
        Column {
            Text(stringResource(R.string.edit_categories))
            Text(dish.getName(), style = AppTypography.bodyMedium)
        }
    }, text = {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            categories.forEach { category ->
                Text("${category.key}:", style = AppTypography.titleMedium)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    category.value.forEach { cat ->
                        FilterChip(selected = selectedCategories.contains(cat), onClick = {
                            selectedCategories = if (selectedCategories.contains(cat)) {
                                selectedCategories - cat
                            } else {
                                selectedCategories + cat
                            }
                        }, label = { Text(cat) })
                    }
                }
            }
        }
    }, confirmButton = {
        OutlinedButton(onClick = {
            val updatedDish = dish.copy(
                categories = selectedCategories.toList()
            )
            onSave(updatedDish)
        }) {
            Text(stringResource(R.string.save))
        }
    }, dismissButton = {
        OutlinedButton(onClick = onDismiss) {
            Text(stringResource(R.string.cancel))
        }
    })
}