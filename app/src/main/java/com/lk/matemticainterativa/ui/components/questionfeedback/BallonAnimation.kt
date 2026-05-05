package com.lk.matemticainterativa.ui.components.questionfeedback

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lk.matemticainterativa.R
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
            animation = tween(3500, easing = LinearEasing)
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.15f))
    ) {
        // 🎈 Balloon 1
        Balloon(
            xFraction = 0.3f,
            offsetY = offsetY - 200f,
            id = R.drawable.ballon_green
        )

        // 🎈 Balloon 2
        Balloon(
            xFraction = 0.1f,
            offsetY = offsetY - 400f,
            id = R.drawable.ballon_yellow
        )

        // 🎈 Balloon 3
        Balloon(
            xFraction = 0.3f,
            offsetY = offsetY - 600f,
            id = R.drawable.ballon_red
        )

        Text(
            text = "🎉 Parabéns!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.White, shape = RoundedCornerShape(8.dp)) // white box
                .border(2.dp, Color.Black, shape =
                    RoundedCornerShape(8.dp)) // border
                .padding(horizontal = 16.dp, vertical = 8.dp)) // space inside box        )
    }
}