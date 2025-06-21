package com.jaguar.littlelemon.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.DishDetailsScreen
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.DishesViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DishesList(
    navController: NavHostController,
    viewModel: DishesViewModel,
    modifier: Modifier = Modifier,
) {
    val dishes by viewModel.dishes.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing, onRefresh = { viewModel.fetchDishes() })

    Box(
        modifier = modifier.pullRefresh(refreshState)
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(1)) {
            if (dishes.isNotEmpty()) item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    dishes.forEach { dish ->
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(DishDetailsScreen.createRoute(dish.getName()))
                                }) {
                            DishCard(dish)
                        }
                    }
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
                        text = stringResource(R.string.no_dishes_error),
                        style = AppTypography.bodyLarge.copy(fontSize = 18.sp)
                    )
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
