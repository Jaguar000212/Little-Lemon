package com.jaguar.littlelemon.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.helpers.homeScreen

@Composable
fun NavigationIcon(icon: ImageVector, label: String) {
    Icon(
        icon,
        contentDescription = label,
        modifier = Modifier.padding(horizontal = 20.dp),
        tint = colorResource(
            id = R.color.yellow
        )
    )
}

@Composable
fun Drawer(naveController: NavHostController, state: DrawerState, content: @Composable () -> Unit) {
    ModalNavigationDrawer(
        drawerState = state,
        drawerContent = {
            ModalDrawerSheet {
                Text("Little Lemon Restaurant", modifier = Modifier.padding(16.dp))
                HorizontalDivider()

                NavigationDrawerItem(
                    label = {
                        Text("Home")
                    },
                    selected = false,
                    icon = { NavigationIcon(Icons.Outlined.Home, "Home") },
                    onClick = {
                        naveController.navigate(homeScreen.route)
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text("Reserve a Table")
                    },
                    icon = { NavigationIcon(Icons.Outlined.DateRange, "Reserve a Table") },
                    selected = false,
                    onClick = { /*TODO*/ }
                )

                /* More items*/

                HorizontalDivider()
                NavigationDrawerItem(
                    label = {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Button(onClick = { /*TODO*/ }) {
                                Row {
                                    Icon(
                                        Icons.Outlined.Close,
                                        contentDescription = "Exit button",
                                        tint = colorResource(
                                            id = R.color.yellow
                                        )
                                    )
                                    Text("Exit App")
                                }
                            }
                        }
                    },
                    selected = false,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 20.dp),
                    onClick = { /*TODO*/ }
                )
            }
        }
    ) {
        content()
    }
}