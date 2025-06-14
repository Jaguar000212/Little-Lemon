package com.jaguar.littlelemon.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.DishDetailsScreen
import com.jaguar.littlelemon.ui.components.DishCard
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.DishesViewModel


@Composable
fun UpperPanel(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.olive))
            .padding(18.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = AppTypography.displayLarge,
            color = colorResource(id = R.color.yellow),
        )
        Text(
            text = stringResource(R.string.location),
            style = AppTypography.displayMedium,
            color = colorResource(id = R.color.white),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp)
        ) {
            Text(
                text = stringResource(id = R.string.description),
                style = AppTypography.bodyLarge.copy(
                    fontSize = 24.sp,
                ),
                color = colorResource(id = R.color.white),
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Image(
                painter = painterResource(id = R.drawable.chef),
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(18.dp))
            )
        }
    }
}

@Composable
fun Dishes(
    navController: NavHostController,
    viewModel: DishesViewModel,
    modifier: Modifier = Modifier,
) {
    val dishes by viewModel.dishes.collectAsState()
    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = modifier) {
        item {
            UpperPanel()
        }
        item {
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
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier, navController: NavHostController, viewModel: DishesViewModel
) {
    Column(modifier = modifier) {
        Dishes(navController, viewModel)
    }
}

