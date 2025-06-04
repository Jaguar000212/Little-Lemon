package com.jaguar.littlelemon.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.KeyboardType
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
    var phone: String by remember { mutableStateOf("") }
    var name: String by remember { mutableStateOf("") }
    var preference: Boolean by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registration", fontSize = 24.sp, color = colorResource(R.color.yellow),
            )

            TextField(
                value = email ?: "",
                leadingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = "Email Icon")
                },
                enabled = false,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                onValueChange = { },
                label = { Text(text = "E-mail") },
                modifier = Modifier
                    .padding(48.dp, 16.dp, 48.dp, 0.dp)
                    .fillMaxWidth()
            )

            TextField(
                value = name,
                leadingIcon = {
                    Icon(Icons.Outlined.Person, contentDescription = "Person Icon")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                onValueChange = { name = it },
                label = { Text(text = "Name") },
                modifier = Modifier
                    .padding(48.dp, 16.dp, 48.dp, 0.dp)
                    .fillMaxWidth()
            )

            TextField(
                value = phone,
                leadingIcon = {
                    Icon(Icons.Outlined.Phone, contentDescription = "Phone Icon")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                onValueChange = { phone = it },
                label = { Text(text = "Phone") },
                modifier = Modifier
                    .padding(48.dp, 16.dp, 48.dp, 0.dp)
                    .fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    text = "Preference - Non-Vegetarian",
                    modifier = Modifier
                        .padding(48.dp, 16.dp, 48.dp, 0.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )

                Switch(
                    checked = preference,
                    onCheckedChange = { preference = it },
                    modifier = Modifier
                        .padding(48.dp, 16.dp, 48.dp, 0.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
            }

            Button(
                onClick = {
                    currentUser?.updateData(
                        name = name,
                        phone = phone,
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