package com.jaguar.littlelemon.screens


import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.components.DishCard
import com.jaguar.littlelemon.helpers.dishDetailsPane


@Composable
fun UpperPanel(modifier: Modifier = Modifier) {
    val context = LocalContext.current
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
        Button(
            onClick = {
                Toast.makeText(
                    context, "Order received. Thank you!", Toast.LENGTH_SHORT
                ).show()
            }, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.yellow)
            ), modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Order Take Away",
                fontSize = 18.sp,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun Dishes(navController: NavHostController, modifier: Modifier = Modifier) {
    LazyVerticalGrid(columns = GridCells.Fixed(1), modifier = modifier) {
        items(5) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        navController.navigate(dishDetailsPane.route)
                    }) {
                    DishCard(
                        name = "Greek Salad",
                        description = "The famous Greek salad of crispy lettuce, peppers, olives, our Chicago ...",
                        price = "$12.99",
                        image = R.drawable.dish_1
                    )
                }
                Box(modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        navController.navigate(dishDetailsPane.route)
                    }) {
                    DishCard(
                        name = "Brushetta",
                        description = "Our Bruschetta is made from grilled bread that has been smeared with garlic and...",
                        price = "$9.99",
                        image = R.drawable.dish_2
                    )
                }
                Box(modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        navController.navigate(dishDetailsPane.route)
                    }) {
                    DishCard(
                        name = "Grilled Fish",
                        description = "Our grilled fish is a delicious and healthy option for those who want to eat ...",
                        price = "$15.99",
                        image = R.drawable.dish_3
                    )
                }
                Box(modifier = Modifier
                    .padding(2.dp)
                    .clickable {
                        navController.navigate(dishDetailsPane.route)
                    }) {
                    DishCard(
                        name = "Fried Calamari",
                        description = "Our Fried Calamari is a delicious and crispy appetizer that is perfect for ...",
                        price = "$11.99",
                        image = R.drawable.dish_4
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier) {
        UpperPanel()
        Dishes(navController)
    }
}

