package com.lk.matemticainterativa.ui.components.questionfeedback


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lk.matemticainterativa.R

@Composable
fun Balloon(
    xFraction: Float,
    offsetY: Float,
    color: Color
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ballon),
            contentDescription = "Balloon",
            colorFilter = ColorFilter.tint(color), // 🎨 CHANGE COLOR HERE
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(
                    x = (xFraction * 300).dp, // adjust based on screen
                    y = offsetY.dp
                )
                .size(300.dp)
        )
    }
}