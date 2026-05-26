package com.lk.matemticainterativa.ui.menu.submenus

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lk.matemticainterativa.ui.menu.MenuItem

@Composable
fun VectorsSubMenuScreen(navController: NavController) {

    val isDark = isSystemInDarkTheme()

    val contentColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else
        MaterialTheme.colorScheme.background

    BackHandler(enabled = true) { navController.navigate("main/") }
    LazyColumn(modifier = Modifier
        .background(contentColor)
        .fillMaxSize(),
        contentPadding = contentPadding(16.dp, 30.dp)) {

        item {
            MenuItem("Exercício 1") {
                navController.navigate("vectors/1")
            }
        }
        item {
            MenuItem("Exercício 2") {
                navController.navigate("vectors/2")
            }
        }
        item {
            MenuItem("Exercício 3") {
                navController.navigate("vectors/3")
            }
        }
        item {
            MenuItem("Exercício 4") {
                navController.navigate("vectors/4")
            }
        }
        item {
            MenuItem("Exercício 5") {
                navController.navigate("vectors/5")
            }
        }
        item {
            MenuItem("Exercício 6") {
                navController.navigate("vectors/6")
            }
        }
        item {
            MenuItem("Exercício 7") {
                navController.navigate("vectors/7")
            }
        }
        item {
            MenuItem("Exercício 8") {
                navController.navigate("vectors/8")
            }
        }
        item {
            MenuItem("Exercício 9") {
                navController.navigate("vectors/9")
            }
        }

    }
}