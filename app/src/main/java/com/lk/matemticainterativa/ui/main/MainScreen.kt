package com.lk.matemticainterativa.ui.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lk.matemticainterativa.ui.components.CombinedTest.CartesianWithDistance
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
        RotatableTriangle(a1 = 400f, b1 = 500f, c1 = 800f, a2 = 300f, b2 = 400f, c2 = 500f)
    }
}