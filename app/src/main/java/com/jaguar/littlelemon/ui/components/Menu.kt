package com.jaguar.littlelemon.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Configs
import com.jaguar.littlelemon.models.Dish
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.MenuViewModel

@OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class
)
@Composable
fun Menu(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    menuViewModel: MenuViewModel,
    actions: @Composable (Dish) -> Unit = { }
) {
    var showFilterDialog by remember { mutableStateOf(false) }

    val allDishes by menuViewModel.dishes.collectAsState()
    val categories by Configs.categories.collectAsState()
    val types: List<String> by Configs.types.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategories: Set<String> by remember { mutableStateOf(emptySet()) }
    var selectedType: String by remember { mutableStateOf("") }

    val isRefreshing by menuViewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing, onRefresh = { menuViewModel.fetchDishes() })

    val dishes by remember(allDishes, searchQuery, selectedCategories, selectedType) {
        derivedStateOf {
            allDishes.filter { dish ->
                searchQuery.isBlank() || dish.getName().contains(searchQuery, ignoreCase = true)
            }.filter { dish ->
                selectedCategories.isEmpty() || dish.getCategories()
                    .any { it in selectedCategories }
            }.filter { dish ->
                selectedType.isEmpty() || dish.getType() == selectedType
            }
        }
    }

    if (showFilterDialog) {
        ModalBottomSheet(
            onDismissRequest = { showFilterDialog = false }, modifier = Modifier.padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Apply Filters", style = AppTypography.headlineLarge)
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
        }
    }

    Box(modifier = modifier.pullRefresh(refreshState)) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text(stringResource(R.string.search_dishes)) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search, contentDescription = "Search"
                        )
                    })

                IconButton(
                    onClick = { showFilterDialog = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(colorResource(R.color.yellow), CircleShape)
                ) {
                    Icon(
                        painterResource(R.drawable.filter),
                        contentDescription = "Filter",
                        tint = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                types.forEach { t ->
                    FilterChip(selected = t == selectedType, onClick = {
                        selectedType = if (selectedType == t) ""
                        else t
                    }, label = {
                        Text(t)
                    })
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(1)
            ) {
                if (dishes.isNotEmpty()) items(dishes) { dish ->
                    Box(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        DishCard(dish = dish, navController = navController, actions = actions)
                    }
                }
                else item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Warning,
                            contentDescription = "Warning",
                            tint = colorResource(R.color.yellow),
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = stringResource(R.string.no_dishes_error_toast),
                            style = AppTypography.bodyLarge.copy(fontSize = 18.sp)
                        )
                    }
                }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}
