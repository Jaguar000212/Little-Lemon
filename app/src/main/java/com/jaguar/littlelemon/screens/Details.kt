package com.jaguar.littlelemon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DishDetails(
    dishImage: Int, dishName: String, dishDesc: String, modifier: Modifier
) {
    var quantity: Int by remember { mutableIntStateOf(1) }
    Column(
        horizontalAlignment = Alignment.Start, modifier = modifier
    ) {
        Text(
            text = dishName,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = dishDesc,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.75f)
            )
            Image(
                painter = painterResource(id = dishImage),
                contentDescription = "Dish Image",
                modifier = Modifier.padding(16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(Icons.Outlined.KeyboardArrowUp,
                contentDescription = "Increase Quantity",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        if (quantity < 10) quantity++
                    })
            Text(
                text = "Quantity: $quantity",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(16.dp)
            )
            Icon(Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Decrease Quantity",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        if (quantity > 1) {
                            quantity--
                        }
                    })
        }
    }
}

