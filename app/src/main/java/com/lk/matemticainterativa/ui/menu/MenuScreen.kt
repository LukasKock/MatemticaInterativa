package com.lk.matemticainterativa.ui.menu

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MenuScreen(navController: NavController) {

    LazyColumn(Modifier.padding(0.dp, 20.dp)) {

        item {
            MenuItem("Semelhança de Triângulos") {
                navController.navigate("triangles/")
            }
        }
        item {
            MenuItem("Plano Cartesiano") {
                navController.navigate("cartesian/")
            }
        }
        item {
            MenuItem("Vetores") {
                navController.navigate("vectors/")
            }
        }

    }
}