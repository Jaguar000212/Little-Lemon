package com.jaguar.littlelemon.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.viewModel.UserViewModel

@Composable
fun Profile(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val viewModel: UserViewModel = viewModel()
    val currentUser = viewModel.user.collectAsState().value

    if (currentUser == null) {
        Toast.makeText(context, "User data not available", Toast.LENGTH_SHORT).show()
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
                text = "User data is not available.", fontSize = 20.sp
            )
        }
    } else {
        Column(modifier = modifier) {
            Text(
                text = "Name: ${currentUser.getFirstName()} ${currentUser.getLastName()}",
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "Email: ${currentUser.getEmail()}", modifier = Modifier.fillMaxWidth())
            Text(
                text = "Non-Vegetarian: ${if (currentUser.isNonVeg()) "Yes" else "No"}",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}