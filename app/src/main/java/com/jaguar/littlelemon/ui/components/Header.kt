package com.jaguar.littlelemon.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jaguar.littlelemon.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun HamburgerIcon(modifier: Modifier, state: DrawerState, scope: CoroutineScope) {
    Icon(
        imageVector = Icons.Outlined.Menu,
        contentDescription = "menu",
        modifier = modifier
            .size(24.dp)
            .clickable {
                scope.launch {
                    state.apply {
                        if (isClosed) open() else close()
                    }
                }
            },
        tint = (colorResource(id = R.color.yellow))
    )
}

@Composable
fun Logo() {
    Image(
        painter = painterResource(R.drawable.watermark),
        contentDescription = "logo",
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun Header(state: DrawerState, scope: CoroutineScope) {
    Box(
        Modifier
            .height(83.dp)
            .fillMaxWidth()
            .padding(top = 18.dp),
        contentAlignment = Alignment.TopCenter

    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(63.dp)
            ) {
                HamburgerIcon(Modifier.align(Alignment.Center), state, scope)
            }
            Box(modifier = Modifier.height(63.dp)) {
                Logo()
            }
            Box(
                modifier = Modifier.size(63.dp)
            )
        }
    }
}