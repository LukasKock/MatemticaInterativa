package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InvertVectorButton(
    isVisible: Boolean,
    onClick: () -> Unit
){
    if(!isVisible) return

    Box(modifier = Modifier.fillMaxSize()){
        Button(
            modifier = Modifier.padding(60.dp).align(Alignment.BottomCenter),
            onClick = { onClick() }
        ) {
            Text("Inverter vetor")
        }
    }
}