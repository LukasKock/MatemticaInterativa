package com.lk.matemticainterativa.ui.menu

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SubMenuScreen(navController: NavController) {

    LazyColumn(Modifier.padding(0.dp, 30.dp)) {

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

    }
}