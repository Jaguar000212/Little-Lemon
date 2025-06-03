package com.jaguar.littlelemon.screens

import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.HomeScreen


@Composable
fun LoginUI(navController: NavHostController) {
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
        ), contentDescription = "Logo Image", modifier = Modifier.padding(10.dp)
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
        text = "Forgot Password?",
        color = Color(0xFF495E57),
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                if (email.isNotEmpty()) {
                    val auth: FirebaseAuth = FirebaseAuth.getInstance()
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(context.mainExecutor) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "sendPasswordResetEmail:success")
                                Toast.makeText(
                                    context,
                                    "Email has been sent, check your inbox",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Log.w(TAG, "sendPasswordResetEmail:failure", task.exception)
                                Toast.makeText(
                                    context, "Can't find your account", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
                }
            })
    Button(
        onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val auth: FirebaseAuth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context.mainExecutor) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            Toast.makeText(context, "Welcome back!", Toast.LENGTH_SHORT).show()
                            navController.navigate(HomeScreen.route)
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }, colors = ButtonDefaults.buttonColors(
            Color(0xFF495E57)
        ), modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Login", color = Color(0xFFEDEFEE)
        )
    }

}

@Composable
fun LoginPanel(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginUI(navController)
    }
}
