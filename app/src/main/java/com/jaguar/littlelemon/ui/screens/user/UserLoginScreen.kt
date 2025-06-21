package com.jaguar.littlelemon.ui.screens.user

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.UserHomeScreen
import com.jaguar.littlelemon.ui.theme.AppTypography
import com.jaguar.littlelemon.viewModel.UserViewModel


@Composable
fun UserLoginPanel(navController: NavHostController, userViewModel: UserViewModel) {
    val context = LocalContext.current
    var email: String by remember {
        mutableStateOf("")
    }
    var password: String by remember {
        mutableStateOf("")
    }
    Image(
        painter = painterResource(
            id = R.drawable.logo
        ),
        contentDescription = stringResource(R.string.logo_image_desc),
        modifier = Modifier.padding(8.dp)
    )
    TextField(
        value = email,
        leadingIcon = {
            Icon(Icons.Outlined.Email, contentDescription = "Email Icon")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email, showKeyboardOnFocus = true
        ),
        onValueChange = { email = it },
        label = { Text(text = "E-mail") },
        modifier = Modifier
            .padding(48.dp, 16.dp, 48.dp, 0.dp)
            .fillMaxWidth()
    )
    TextField(
        value = password,
        leadingIcon = {
            Icon(Icons.Outlined.Lock, contentDescription = "Password Icon")
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = { password = it },
        label = { Text(text = "Password") },
        modifier = Modifier
            .padding(48.dp, 16.dp, 48.dp, 0.dp)
            .fillMaxWidth()
    )

    Text(
        text = stringResource(R.string.forgot_password),
        color = colorResource(R.color.olive),
        style = AppTypography.labelLarge,
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                if (email.isNotEmpty()) {
                    userViewModel.forgotPassword(email)
                        .addOnCompleteListener(context.mainExecutor) { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.forgot_password_confirmation_toast),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.forgot_password_error_toast),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        context,
                        context.getString(R.string.forgot_password_noEmail_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    Button(
        onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.logIn(email, password)
                    .addOnCompleteListener(context.mainExecutor) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.login_confirm_toast), Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(UserHomeScreen.route) {
                                popUpTo(UserHomeScreen.route) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else Toast.makeText(
                context,
                context.getString(R.string.missing_fields_error_toast), Toast.LENGTH_SHORT
            ).show()
        }, modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "Login", style = AppTypography.labelLarge)
    }

}

@Composable
fun UserLoginScreen(
    modifier: Modifier,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UserLoginPanel(navController, userViewModel)
    }
}
