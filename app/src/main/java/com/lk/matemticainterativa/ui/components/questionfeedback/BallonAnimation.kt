package com.lk.matemticainterativa.ui.components.questionfeedback

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun BalloonAnimation(
    visible: Boolean,
    onFinished: () -> Unit
) {
    if (!visible) return

    // Auto hide after 2 seconds
    LaunchedEffect(visible) {
        if (visible) {
            delay(2000)
            onFinished()
        }
    }


    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 1000f,
        targetValue = -200f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing)
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = Color.Red,
                radius = 40f,
                center = Offset(size.width * 0.3f, offsetY)
            )

            drawCircle(
                color = Color.Blue,
                radius = 50f,
                center = Offset(size.width * 0.7f, offsetY + 300)
            )

            drawCircle(
                color = Color.Green,
                radius = 45f,
                center = Offset(size.width * 0.5f, offsetY + 150)
            )
        }

        Text(
            text = "🎉 Parabéns!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}