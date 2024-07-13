package com.jaguar.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaguar.littlelemon.components.Header
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Header()
                    }
                ) { innerPadding ->
                    Welcome(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Welcome(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "Welcome to",
                color = colorResource(id = R.color.primary),
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                lineHeight = 50.sp,
            )
            Text(
                text = "Little Lemon",
                color = colorResource(id = R.color.primary),
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                lineHeight = 50.sp,
            )
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(16.dp),
                colors = ButtonColors(
                    containerColor = colorResource(id = R.color.primary),
                    contentColor = colorResource(id = R.color.black),
                    disabledContentColor = colorResource(id = R.color.white),
                    disabledContainerColor = colorResource(id = R.color.black),

                    ),
            ) {
                Text(
                    text = "Sign In",
                    color = colorResource(id = R.color.black)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Header()
            },
        ) { innerPadding ->
            Welcome(Modifier.padding(innerPadding))
        }
    }
}