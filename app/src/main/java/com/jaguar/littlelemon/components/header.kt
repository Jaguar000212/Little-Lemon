package com.jaguar.littlelemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.ui.theme.LittleLemonTheme


@Composable
fun HamburgerIcon(modifier: Modifier) {
    Icon(
        painter = painterResource(R.drawable.hamburger_icon),
        contentDescription = "menu",
        modifier = modifier.width(24.dp).height(24.dp)
    )
}

@Composable
fun Logo() {
    Image(painter = painterResource(R.drawable.watermark), contentDescription = "logo")
}

@Composable
fun CartIcon(modifier: Modifier) {
    Icon(painter = painterResource(R.drawable.cart), contentDescription = "cart",
            modifier = modifier.width(24.dp).height(24.dp))
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .height(63.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                HamburgerIcon(Modifier.align(Alignment.Center))
            }
            Box(modifier = Modifier.weight(3f)) {
                Logo()
            }
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                CartIcon(Modifier.align(Alignment.Center))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    LittleLemonTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Header()
        }
    }
}