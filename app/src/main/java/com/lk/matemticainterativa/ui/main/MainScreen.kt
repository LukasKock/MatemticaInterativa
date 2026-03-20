package com.lk.matemticainterativa.ui.main


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lk.matemticainterativa.ui.menu.MenuScreen


@Composable
fun MainScreen(username: String,
               navController: NavController
) {
    val isDark = isSystemInDarkTheme()

    val contentColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else
        MaterialTheme.colorScheme.background


    Scaffold(
        containerColor = contentColor,
        topBar = {
            MainTopBar(
                onSettingsClick = { navController.navigate("menu") },
                onLogoutClick = { navController.navigate("login") }
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize()) {
            MenuScreen(navController)
        }
    }
}