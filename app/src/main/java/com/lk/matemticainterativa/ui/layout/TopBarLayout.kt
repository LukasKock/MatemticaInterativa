package com.lk.matemticainterativa.ui.layout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar() {

    val customShape = GenericShape { size, _ ->

        moveTo(0f, 0f)

        lineTo(size.width, 0f)

        lineTo(size.width, size.height)

        lineTo(0f, size.height)

        close()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clip(customShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF009966),
                        Color(0xFF66D9CC)
                    )
                )
            )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar() {
    TopBar()
}