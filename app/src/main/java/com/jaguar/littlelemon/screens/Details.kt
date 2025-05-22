package com.jaguar.littlelemon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Dish

@Composable
fun DishDetails(
    dish: Dish, modifier: Modifier = Modifier
) {
    var quantity: Int by remember { mutableIntStateOf(1) }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(dish.imageURL).crossfade(true)
            .placeholder(R.drawable.logo).error(R.drawable.cross).build()
    )
    Column(
        horizontalAlignment = Alignment.Start, modifier = modifier
    ) {
        Text(
            text = dish.name,
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
                text = dish.description,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.75f)
            )
            Image(
                painter = painter,
                contentDescription = "Dish image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .fillMaxSize()
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                Icons.Outlined.KeyboardArrowUp,
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
            Icon(
                Icons.Outlined.KeyboardArrowDown,
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

