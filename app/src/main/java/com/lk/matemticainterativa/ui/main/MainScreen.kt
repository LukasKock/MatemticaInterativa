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
import com.lk.matemticainterativa.ui.components.CartesianCanvas
import com.lk.matemticainterativa.ui.components.similarTriangles.SimilarTriangles
import com.lk.matemticainterativa.ui.menu.MenuScreen


@Composable
fun MainScreen(username: String,
               navController: NavController,
               onLogout: () -> Unit = {},
               onSettings: () -> Unit = {}) {
    val isDark = isSystemInDarkTheme()

    val containerColor = if (isDark) Color.Black else Color.White
    val contentColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else
        MaterialTheme.colorScheme.background


    Scaffold(
        containerColor = contentColor,
        topBar = {
            MainTopBar(
                onSettingsClick = onSettings,
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