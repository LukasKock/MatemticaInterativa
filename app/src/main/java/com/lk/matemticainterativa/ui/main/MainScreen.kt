package com.lk.matemticainterativa.ui.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.lk.matemticainterativa.ui.components.triangle.RotatableTriangle


@Composable
fun MainScreen(username: String,
               onLogout: () -> Unit = {},
               onSettings: () -> Unit = {}) {
    Scaffold(
        topBar = {
            MainTopBar(
                onSettingsClick = onSettings,
                onLogoutClick = onLogout
            )
        }
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding).padding(16.dp)) {
        }
        RotatableTriangle(
            a1 = 600f, b1 = 800f, c1 = 1000f,
            a2 = 300f, b2 = 400f, c2 = 500f,
            initialOffset1 = Offset(0f, 0f),
            initialOffset2 = Offset(0f, 0f),
            initialRotation1 = 0f,
            initialRotation2 = 0f,
            initialScale1 = 2f,
            initialScale2 = 1f)
    }
}