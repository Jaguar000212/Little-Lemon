package com.jaguar.littlelemon.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.HomeScreen
import com.jaguar.littlelemon.viewModel.UserViewModel

@Composable
fun DataCollectionUI(
    navController: NavHostController
) {
    val viewModel = UserViewModel()
    val currentUser = viewModel.user.collectAsState().value
    val email = currentUser?.getEmail()
    var firstName: String by remember { mutableStateOf("") }
    var lastName: String by remember { mutableStateOf("") }
    var preference: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registration", fontSize = 24.sp, color = colorResource(id = R.color.yellow)
            )

            TextField(
                value = email ?: "",
                onValueChange = { },
                label = { Text(text = "E-Mail") },
                readOnly = true,
                modifier = Modifier.padding(10.dp)
            )

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = "First Name") },
                modifier = Modifier.padding(10.dp)
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = "Last Name") },
                modifier = Modifier.padding(10.dp)
            )

            Row {
                Text(
                    text = "Preference - Non-Vegetarian",
                    modifier = Modifier.padding(10.dp)
                )

                Switch(
                    checked = preference,
                    onCheckedChange = { preference = it },
                    modifier = Modifier.padding(10.dp)
                )
            }

            Button(
                onClick = {
                    currentUser?.updateData(
                        firstName = firstName,
                        lastName = lastName,
                        nonVeg = preference
                    )
                    navController.navigate(HomeScreen.route)
                }, modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 0.dp)
            ) {
                Text(text = "Continue to Home")
            }
        }
    }
}

@Composable
fun DataCollectionPanel(
    modifier: Modifier = Modifier, navController: NavHostController
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DataCollectionUI(navController)
    }
}