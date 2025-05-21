package com.jaguar.littlelemon.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.helpers.HomeScreen
import java.util.Objects.equals

fun verify(username: String, password: String): Boolean {
    return equals(username, "Jaguar") && equals(password, "000212")
}

@Composable
fun LoginUI(navController: NavHostController) {
    val context = LocalContext.current
    var username: String by remember {
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
        value = username,
        onValueChange = { username = it },
        label = { Text(text = "Username") },
        modifier = Modifier.padding(10.dp)
    )
    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text(text = "Password") },
        modifier = Modifier.padding(10.dp)
    )
    Button(
        onClick = {
            if (verify(username, password)) {
                Toast.makeText(context, "Welcome to Little Lemon!", Toast.LENGTH_SHORT).show()
                navController.navigate(HomeScreen.route)
            } else {
                Toast.makeText(context, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
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
