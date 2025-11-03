package com.lk.matemticainterativa.ui.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lk.matemticainterativa.ui.components.RotatableEquilateralTriangle
import com.lk.matemticainterativa.ui.components.RotatableTriangle


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
        RotatableTriangle(a = 400f, b = 500f, c = 800f)
    }
}