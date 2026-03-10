package com.lk.matemticainterativa.ui.components.similarTriangles

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedFloatButton(
    isTriangle1Selected: Boolean,
    isTriangle2Selected: Boolean,
    initialTilt1: Float,
    initialTilt2: Float,
    onTiltChange: (Float) -> Unit
) {
    val initToggle1 = initialTilt1 != -1f
    val initToggle2 = initialTilt2 != -1f

    var toggled1 by rememberSaveable { mutableStateOf(initToggle1) }
    var toggled2 by rememberSaveable { mutableStateOf(initToggle2) }

    // Animate between -1f and 1f when switch changes
    val animatedFloat1 by animateFloatAsState(
        targetValue = if (toggled1) 1f else -1f,
        animationSpec = tween(
            durationMillis = 500, // 500 milliseconds
            easing = LinearEasing
        ),
        label = "FloatAnimation"
    )
    val animatedFloat2 by animateFloatAsState(
        targetValue = if (toggled2) 1f else -1f,
        animationSpec = tween(
            durationMillis = 500, // 500 milliseconds
            easing = LinearEasing
        ),
        label = "FloatAnimation"
    )
    // Whenever the animation updates, assign it to your tilt
    LaunchedEffect(animatedFloat1) {
        onTiltChange(animatedFloat1)
    }
    LaunchedEffect(animatedFloat2) {
        onTiltChange(animatedFloat2)
    }

    // Theme colors
    val isDark = isSystemInDarkTheme()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if(isTriangle1Selected){
                    toggled1 = !toggled1
                } else if(isTriangle2Selected){
                    toggled2 = !toggled2
                }
            },
            enabled = isTriangle1Selected || isTriangle2Selected,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDark) colorScheme.primary else colorScheme.secondary,
                contentColor = colorScheme.onPrimary,
                disabledContentColor = if(isDark) Color(0xFF131212) else Color(0xFF808080),
                disabledContainerColor = if(isDark) Color(0xFF7F7F83) else Color(0xFFE1DEDE)
            )
        ){
            Text(
                text = "Flip",
                fontSize = 14.sp
            )
        }
    }
}