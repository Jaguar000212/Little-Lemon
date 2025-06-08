package com.jaguar.littlelemon.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Warning
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.HomeScreen
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.UserViewModel

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    navController: NavHostController,
    incomplete: Boolean = false
) {
    val context = LocalContext.current
    val currentUser = userViewModel.user.collectAsState().value
    userViewModel.fetchUserData()

    if (currentUser == null) {
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
                text = stringResource(R.string.no_user_data_error),
                style = AppTypography.bodyLarge.copy(fontSize = 18.sp)
            )
        }
    } else {
        val email = currentUser.getEmail()
        var phone: String by remember { mutableStateOf(currentUser.getPhone()) }
        var name: String by remember { mutableStateOf(currentUser.getName()) }
        var preference: Boolean by remember { mutableStateOf(currentUser.isNonVeg()) }

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "User Profile",
                    style = AppTypography.headlineMedium,
                    color = colorResource(R.color.yellow),
                )

                TextField(
                    value = email,
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

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(48.dp, 16.dp, 48.dp, 0.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Non-Vegetarian",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )

                    Switch(
                        checked = preference,
                        onCheckedChange = { preference = it },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    )
                }

                Button(
                    onClick = {
                        userViewModel.updateData(
                            name = name, phone = phone, nonVeg = preference
                        )
                        Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT)
                            .show()
                        if (incomplete) {
                            navController.navigate(HomeScreen.route) {
                                popUpTo(HomeScreen.route) { inclusive = true }
                            }
                        }
                    }, modifier = Modifier.padding(16.dp, 32.dp, 16.dp, 0.dp)
                ) {
                    Text(text = "Save", style = AppTypography.labelLarge)
                }
            }
        }
    }
}
