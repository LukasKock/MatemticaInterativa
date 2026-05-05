package com.lk.matemticainterativa.ui.components.questionfeedback


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

@Composable
fun Balloon(
    xFraction: Float,
    offsetY: Float,
    id: Int
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        Image(
            painter = painterResource(id),
            contentDescription = "Balloon",
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(
                    x = xFraction * screenWidth, // adjust based on screen
                    y = offsetY.dp
                )
                .size(300.dp)
        )
    }
}