package com.jaguar.littlelemon.screens


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
import com.jaguar.littlelemon.components.DishCard
import com.jaguar.littlelemon.navigation.DishDetailsPane
import com.jaguar.littlelemon.viewModel.DishesViewModel


@Composable
fun UpperPanel(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.olive))
            .padding(20.dp)
    ) {
        Text(
            text = "Little Lemon",
            fontSize = 32.sp,
            color = colorResource(id = R.color.yellow),
        )
        Text(
            text = "Chicago",
            fontSize = 32.sp,
            color = colorResource(id = R.color.white),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.description),
                fontSize = 21.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Image(
                painter = painterResource(id = R.drawable.chef),
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    }
}

@Composable
fun Dishes(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: DishesViewModel = DishesViewModel()
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
                            .padding(10.dp)
                            .clickable {
                                navController.navigate(DishDetailsPane.createRoute(dish.name))
                            }) {
                        DishCard(dish)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier) {
        Dishes(navController)
    }
}

