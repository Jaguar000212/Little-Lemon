package com.jaguar.littlelemon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Dish
import com.jaguar.littlelemon.ui.theme.AppTypography

@Composable
fun DishDetailsScreen(
    dish: Dish, modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(dish.getImageURL())
            .placeholder(R.drawable.image).error(R.drawable.cross).build(),
    )
    Column(
        horizontalAlignment = Alignment.Start, modifier = modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.BottomStart, modifier = Modifier.height(200.dp)
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.dish_image_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .alpha(0.5f)
                    .fillMaxWidth()
            )

            Text(
                text = dish.getName(),
                style = AppTypography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = "Details",
            style = AppTypography.headlineLarge,
            color = colorResource(R.color.yellow),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = dish.getDescription() + "\nPrice: $${dish.getPrice()}" + "\nCalories: ${dish.getCalories()} kcal",
            style = AppTypography.bodyLarge.copy(fontSize = 18.sp),
            modifier = Modifier.padding(8.dp, 0.dp)
        )

        Text(
            text = "Ingredients",
            style = AppTypography.headlineLarge,
            color = colorResource(R.color.yellow),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = dish.getFormattedIngredients(),
            style = AppTypography.bodyLarge.copy(fontSize = 18.sp),
            modifier = Modifier.padding(8.dp, 0.dp)
        )
    }
}
