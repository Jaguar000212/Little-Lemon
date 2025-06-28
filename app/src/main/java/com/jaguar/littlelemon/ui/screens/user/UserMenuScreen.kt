package com.jaguar.littlelemon.ui.screens.user

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.ui.components.Menu
import com.jaguar.littlelemon.viewModel.MenuViewModel
import com.jaguar.littlelemon.viewModel.UserViewModel

@Composable
fun UserMenuScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    menuViewModel: MenuViewModel,
    userViewModel: UserViewModel
) {
    val user = userViewModel.user.collectAsState().value ?: return
    var userFavourites by remember(user) {
        mutableStateOf(user.getFavorites())
    }
    val context = LocalContext.current
    Menu(modifier, navController, menuViewModel) { dish ->
        val isFavourite = dish.getId() in userFavourites
        IconButton(
            {
                if (isFavourite) {
                    userFavourites -= dish.getId()
                    Toast.makeText(context, "Removed from favourites", Toast.LENGTH_SHORT).show()
                } else {
                    userFavourites += dish.getId()
                    Toast.makeText(context, "Added to favourites", Toast.LENGTH_SHORT).show()
                }
                userViewModel.updateData(user.copy(favorites = userFavourites))
            }, colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.Red.copy(alpha = 0.5f),
            )
        ) {
            Icon(
                imageVector = if (isFavourite) Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder,
                contentDescription = if (isFavourite) stringResource(R.string.remove_favourites_desc)
                else stringResource(R.string.add_favourites_desc)
            )
        }
    }
}