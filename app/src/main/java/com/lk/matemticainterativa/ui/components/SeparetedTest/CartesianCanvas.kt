package com.lk.matemticainterativa.ui.components.SeparetedTest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp

@Composable
fun CartesianCanvas(
    offset: Offset,
    scale: Float,
    moveEnabled: Boolean = true
    ) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val w = size.width
        val h = size.height
        val origin = Offset(w / 2f + offset.x - 350f, h / 2f + offset.y + 600f)

        // draw grid
        val unitsPerTick = 1f
        val stepPx = scale * unitsPerTick
        if (stepPx > 5f) {
            // vertical lines
            var x = origin.x % stepPx
            while (x < w) {
                drawLine(
                    color = Color(0xFFE0E0E0),
                    start = Offset(x, 0f),
                    end = Offset(x, h),
                    strokeWidth = 1f
                )
                x += stepPx
            }
            // horizontal
            var y = origin.y % stepPx
            while (y < h) {
                drawLine(
                    color = Color(0xFFE0E0E0),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1f
                )
                y += stepPx
            }
        }
        // draw axes
        drawLine(color = Color.Black, start = Offset(0f, origin.y), end = Offset(w, origin.y), strokeWidth = 3f)
        drawLine(color = Color.Black, start = Offset(origin.x, 0f), end = Offset(origin.x, h), strokeWidth = 3f)

        // ticks and labels using nativeCanvas for text
        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = 14.sp.toPx()
            isAntiAlias = true
        }

        // x ticks
        var step = 1
        var x = origin.x + stepPx
        while (x < w) {
            drawLine(color = Color.Black, start = Offset(x, origin.y - 6f), end = Offset(x, origin.y + 6f), strokeWidth = 2f)
            val valX = step * unitsPerTick
            val label = if (valX == 0f) "0" else valX.toString()
            drawContext.canvas.nativeCanvas.drawText(label, x - 10f, origin.y + 20f, textPaint)
            x += stepPx
            step++
        }
        // negative x
        step = -1
        x = origin.x - stepPx
        while (x > 0f) {
            drawLine(color = Color.Black, start = Offset(x, origin.y - 6f), end = Offset(x, origin.y + 6f), strokeWidth = 2f)
            val valX = step * unitsPerTick
            val label = valX.toString()
            drawContext.canvas.nativeCanvas.drawText(label, x - 10f, origin.y + 20f, textPaint)
            x -= stepPx
            step--
        }
        // y ticks positive (up)
        step = 1
        var y = origin.y - stepPx
        while (y > 0f) {
            drawLine(color = Color.Black, start = Offset(origin.x - 6f, y), end = Offset(origin.x + 6f, y), strokeWidth = 2f)
            val valY = step * unitsPerTick
            val label = valY.toString()
            drawContext.canvas.nativeCanvas.drawText(label, origin.x + 10f, y + 6f, textPaint)
            y -= stepPx
            step++
        }
        // y negative (down)
        step = -1
        y = origin.y + stepPx
        while (y < h) {
            drawLine(color = Color.Black, start = Offset(origin.x - 6f, y), end = Offset(origin.x + 6f, y), strokeWidth = 2f)
            val valY = step * unitsPerTick
            val label = valY.toString()
            drawContext.canvas.nativeCanvas.drawText(label, origin.x + 10f, y + 6f, textPaint)
            y += stepPx
            step--
        }
//        // Example: draw the function y = sin(x) in cartesian coords
//        // map cartesian x to screen: screenX = origin.x + (cartX * scale)
//        // screenY = origin.y - (cartY * scale)
//        // we sample several cartesian x points
//        val pathPoints = mutableListOf<Offset>()
//        val cartXStart = - (origin.x / scale) - 2f
//        val cartXEnd = ( (w - origin.x) / scale ) + 2f
//        val samples = 500
//        for (i in 0..samples) {
//            val t = i / samples.toFloat()
//            val cartX = cartXStart + t * (cartXEnd - cartXStart)
//            val cartY = kotlin.math.sin(cartX) // example
//            val sx = origin.x + cartX * scale
//            val sy = origin.y - cartY * scale
//            pathPoints.add(Offset(sx, sy))
//        }
//        // draw polyline
//        for (i in 0 until pathPoints.lastIndex) {
//            drawLine(color = Color.Blue, start = pathPoints[i], end = pathPoints[i+1], strokeWidth = 2f)
//        }
    }
}