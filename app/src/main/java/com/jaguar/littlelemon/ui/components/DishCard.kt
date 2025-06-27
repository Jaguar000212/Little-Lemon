package com.jaguar.littlelemon.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.models.Dish
import com.jaguar.littlelemon.ui.theme.AppTypography

private const val DESCRIPTION_MAX_LENGTH = 80

@Composable
fun DishCard(
    dish: Dish, modifier: Modifier = Modifier, actions: @Composable (Dish) -> Unit = { }
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(dish.getImageURL()).placeholder(R.drawable.image)
            .error(R.drawable.cross).build()
    )

    Card(
        modifier = modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = dish.getName(),
                    style = AppTypography.bodyLarge.copy(fontSize = 18.sp),
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = if (dish.getDescription().length < DESCRIPTION_MAX_LENGTH) dish.getDescription()
                    else dish.getDescription().substring(0, DESCRIPTION_MAX_LENGTH) + "...",
                    style = AppTypography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "$${dish.getPrice()}",
                    style = AppTypography.bodyMedium,
                    color = Color.LightGray,
                    fontWeight = FontWeight.Bold,
                )
                actions(dish)
            }

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.dish_image_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }
    }

}
