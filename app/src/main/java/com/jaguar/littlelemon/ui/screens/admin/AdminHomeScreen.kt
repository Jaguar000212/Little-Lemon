package com.jaguar.littlelemon.ui.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jaguar.littlelemon.R
import com.jaguar.littlelemon.navigation.AdminMenuScreen
import com.jaguar.littlelemon.ui.theme.AppTypography

@Composable
fun AdminHomeScreen(modifier: Modifier, navController: NavHostController) {
    val adminActions = listOf(
        AdminAction(
            stringResource(R.string.manage_menu),
            stringResource(R.string.manage_menu_desc)
        ) {
            navController.navigate(AdminMenuScreen.route)
        })

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.admin_panel),
            style = AppTypography.headlineMedium,
            color = colorResource(R.color.yellow),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(adminActions) { action ->
                AdminActionCard(action)
            }
        }
    }
}

data class AdminAction(
    val title: String, val description: String, val onClick: () -> Unit
)

@Composable
fun AdminActionCard(action: AdminAction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { action.onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = action.title,
                style = AppTypography.bodyLarge.copy(
                    fontSize = 18.sp,
                ),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = action.description,
                style = AppTypography.bodyMedium,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(0.75f)
            )
        }
    }
}

