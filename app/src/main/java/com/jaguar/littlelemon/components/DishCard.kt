package com.jaguar.littlelemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DishCard(name: String, description: String, price: String, image: Int) {
    Card {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = description,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(0.75f)
                )
                Text(
                    text = price,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                )
            }
            Image(
                painter = painterResource(id = image),
                contentDescription = name,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}

