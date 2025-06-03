package com.jaguar.littlelemon.screens

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.DataCollection


@Composable
fun RegistrationUI(navController: NavHostController) {
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

    Button(
        onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                val auth: FirebaseAuth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context.mainExecutor) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "RegisterWithEmail:success")
                            Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT)
                                .show()
                            auth.signInWithEmailAndPassword(email, password)
                            navController.navigate(DataCollection.route)
                        } else {
                            Log.w(TAG, "RegistrationWithEmail:failure", task.exception)
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(
                                    context, "Email already registered.", Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    context,
                                    "Registration failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
            } else Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()

        }, colors = ButtonDefaults.buttonColors(
            Color(0xFF495E57)
        ), modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = "Next", color = Color(0xFFEDEFEE)
        )
    }
}

@Composable
fun RegistrationPanel(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RegistrationUI(navController)
    }
}
