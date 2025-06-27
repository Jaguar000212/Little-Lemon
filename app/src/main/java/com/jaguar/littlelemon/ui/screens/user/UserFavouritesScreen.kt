package com.jaguar.littlelemon.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.ui.components.DishCard
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.MenuViewModel
import com.jaguar.littlelemon.viewModel.UserViewModel

@Composable
fun UserFavouritesScreen(
    modifier: Modifier,
    navController: NavHostController,
    menuViewModel: MenuViewModel,
    userViewModel: UserViewModel
) {
    val user = userViewModel.user.collectAsState().value ?: return
    var userFavourites by remember(user) {
        mutableStateOf(user.getFavorites())
    }
    val context = LocalContext.current
    val allDishes by menuViewModel.dishes.collectAsState()

    val dishes = allDishes.filter { it.getId() in userFavourites }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (dishes.isNotEmpty()) item {
            Text(
                text = stringResource(R.string.your_favourites),
                style = AppTypography.headlineLarge,
                color = colorResource(id = R.color.yellow),
                modifier = Modifier.padding(16.dp)
            )
        } else item {
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
                    text = stringResource(R.string.no_favourites_yet),
                    style = AppTypography.bodyLarge.copy(fontSize = 18.sp)
                )
            }
        }
        items(dishes) { dish ->
            DishCard(dish = dish, navController = navController) {
                val isFavourite = dish.getId() in userFavourites
                IconButton(
                    {
                        if (isFavourite) {
                            userFavourites -= dish.getId()
                            Toast.makeText(context,
                                context.getString(R.string.removed_from_favourites), Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            userFavourites += dish.getId()
                            Toast.makeText(context,
                                context.getString(R.string.added_to_favourites), Toast.LENGTH_SHORT)
                                .show()
                        }
                        userViewModel.updateData(user.copy(favorites = userFavourites))
                    }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.Red.copy(alpha = 0.5f),
                    )
                ) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isFavourite) stringResource(R.string.remove_from_favourites)
                        else stringResource(R.string.add_to_favourites)
                    )
                }
            }
        }
    }
}