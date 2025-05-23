package com.jaguar.littlelemon.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Dish
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun DishDetails(
    dish: Dish, modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(dish.imageURL)
            .placeholder(R.drawable.image).error(R.drawable.cross).build(),
    )
    Column(
        horizontalAlignment = Alignment.Start, modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = dish.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Image(
            painter = painter,
            contentDescription = "Dish image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp)
                .height(200.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(20.dp))
        )
        Text(
            text = dish.description,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DishDetailsPreview() {
    val dish = Dish(
        name = "Greek Salad",
        description = "A delicious salad with feta cheese, olives, and tomatoes.",
        price = 12.99,
        imageURL = "https://raw.githubusercontent.com/Jaguar000212/Little-Lemon/main/app/src/main/res/drawable-nodpi/dish_2.jpg"
    )
    LittleLemonTheme {
        Scaffold { innerPadding ->
            DishDetails(
                dish = dish, modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
