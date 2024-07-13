package com.jaguar.littlelemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jaguar.littlelemon.R


@Composable
fun HamburgerIcon(modifier: Modifier) {
    Icon(
        painter = painterResource(R.drawable.hamburger_icon),
        contentDescription = "menu",
        modifier = modifier
            .width(24.dp)
            .height(24.dp)
            .clickable { /*TODO*/ },
        tint = (colorResource(id = R.color.primary))
    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(R.drawable.watermark),
        contentDescription = "logo",
        modifier = Modifier
            .padding(8.dp)
    )
}

@Composable
fun CartIcon(modifier: Modifier) {
    Icon(
        painter = painterResource(R.drawable.cart), contentDescription = "cart",
        modifier = modifier
            .width(24.dp)
            .height(24.dp)
            .clickable { /*TODO*/ },
        tint = (colorResource(id = R.color.primary))
    )
}

@Composable
fun Header() {
    Box(
        Modifier
            .height(63.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.black)),
        contentAlignment = Alignment.TopCenter

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(63.dp)
                    .width(63.dp)
            ) {
                HamburgerIcon(Modifier.align(Alignment.Center))
            }
            Box(modifier = Modifier.height(63.dp)) {
                Logo()
            }
            Box(
                modifier = Modifier
                    .height(63.dp)
                    .width(63.dp)
            ) {
                CartIcon(Modifier.align(Alignment.Center))
            }
        }
    }
}