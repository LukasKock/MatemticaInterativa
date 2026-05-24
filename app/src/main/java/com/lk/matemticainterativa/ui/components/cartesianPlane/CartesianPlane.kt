package com.lk.matemticainterativa.ui.components.cartesianPlane

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp

@Composable
fun CartesianPlane(moveEnabled: Boolean = true) {
    var scale by remember { mutableFloatStateOf(180f) }
    var offset by remember { mutableStateOf(Offset.Zero)}

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if(isDark)  Color(0xFF121212) else Color(0xFFFFFFFF)
    val textColor = if (isDark) Color(0xFFE0E0E0) else Color.Black
    val gridColor = if (isDark) Color(0xFF333333) else Color(0xFFE0E0E0)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){
                if (moveEnabled){
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale *= zoom
                        scale = scale.coerceIn(20f, 1000f)
                        offset += pan
                    }
                }
            }
    ) {
        val w = size.width
        val h = size.height
        val origin = Offset(w / 2f, h / 2f)

        val axisMargin = 30f

        // draw grid
        val unitsPerTick = 1f
        val stepPx = scale * unitsPerTick
        if (stepPx > 5f) {
            // vertical lines
            var x = origin.x % stepPx
            while (x < w) {
                drawLine(
                    color = gridColor,
                    start = Offset(x, axisMargin),
                    end = Offset(x, h - axisMargin),
                    strokeWidth = 1f
                )
                x += stepPx
            }
            // horizontal
            var y = origin.y % stepPx
            while (y < h) {
                drawLine(
                    color = gridColor,
                    start = Offset(axisMargin, y),
                    end = Offset(w - axisMargin, y),
                    strokeWidth = 1f
                )
                y += stepPx
            }
        }


        // ticks and labels using nativeCanvas for text
        val textPaint = Paint().apply {
            color = textColor.toArgb()
            textSize = 14.sp.toPx()
            isAntiAlias = true
        }

        // x ticks
        var step = 1
        var x = origin.x + stepPx
        while (x < w ) {
            drawLine(color = textColor, start = Offset(x, origin.y - 6f), end = Offset(x, origin.y + 6f), strokeWidth = 2f)
            val valX = step * unitsPerTick
            val label = if (valX == 0f) "0" else valX.toString()
            drawContext.canvas.nativeCanvas.drawText(label, x - 10f, origin.y + 40f, textPaint)
            x += stepPx
            step++
        }
        // X axis
        drawArrow(
            start = Offset(axisMargin, origin.y),
            end = Offset(w - axisMargin, origin.y),
            color = textColor
        )
        // negative x
        step = -1
        x = origin.x - stepPx
        while (x > 0f) {
            drawLine(color = textColor, start = Offset(x, origin.y - 6f), end = Offset(x, origin.y + 6f), strokeWidth = 2f)
            val valX = step * unitsPerTick
            val label = valX.toString()
            drawContext.canvas.nativeCanvas.drawText(label, x - 10f, origin.y + 40f, textPaint)
            x -= stepPx
            step--
        }
        // y ticks positive (up)
        step = 1
        var y = origin.y - stepPx
        while (y > 0f) {
            drawLine(color = textColor, start = Offset(origin.x - 6f, y), end = Offset(origin.x + 6f, y), strokeWidth = 2f)
            val valY = step * unitsPerTick
            val label = valY.toString()
            drawContext.canvas.nativeCanvas.drawText(label, origin.x + 10f, y + 6f, textPaint)
            y -= stepPx
            step++
        }
        // Y axis
        drawArrow(
            start = Offset(origin.x, h - axisMargin),
            end = Offset(origin.x, axisMargin),
            color = textColor
        )

        // axis labels
        drawContext.canvas.nativeCanvas.drawText(
            "x",
            w - axisMargin - 35f,
            origin.y + 40f,
            textPaint
        )

        drawContext.canvas.nativeCanvas.drawText(
            "y",
            origin.x + 25f,
            axisMargin + 35f,
            textPaint
        )
        // y negative (down)
        step = -1
        y = origin.y + stepPx
        while (y < h) {
            drawLine(color = textColor, start = Offset(origin.x - 6f, y), end = Offset(origin.x + 6f, y), strokeWidth = 2f)
            val valY = step * unitsPerTick
            val label = valY.toString()
            drawContext.canvas.nativeCanvas.drawText(label, origin.x + 10f, y + 6f, textPaint)
            y += stepPx
            step--
        }
    }
}