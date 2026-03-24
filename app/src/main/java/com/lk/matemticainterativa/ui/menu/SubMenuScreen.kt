package com.lk.matemticainterativa.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SubMenuScreen(navController: NavController) {

    val isDark = isSystemInDarkTheme()

    val contentColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else
        MaterialTheme.colorScheme.background

    LazyColumn(modifier = Modifier
        .background(contentColor)
        .fillMaxSize(),
        contentPadding = contentPadding(16.dp, 30.dp)) {

        item {
            MenuItem("Exercício 1") {
                navController.navigate("triangles/1")
            }
        }
        item {
            MenuItem("Exercício 2") {
                navController.navigate("triangles/2")
            }
        }
        item {
            MenuItem("Exercício 3") {
                navController.navigate("triangles/3")
            }
        }
        item {
            MenuItem("Exercício 4") {
                navController.navigate("triangles/4")
            }
        }
        item {
            MenuItem("Exercício 5") {
                navController.navigate("triangles/5")
            }
        }

    }
}