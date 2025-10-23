package com.lk.matemticainterativa.ui.components.CombinedTest

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun CartesianWithDistance() {
    var scale by remember { mutableFloatStateOf(180f) }
    var offsetPoints by remember { mutableStateOf(Offset.Zero) }

    var offsetPlain by remember { mutableStateOf(Offset.Zero) }

    var pointA by remember { mutableStateOf(Offset(200f, 200f)) }
    var pointB by remember { mutableStateOf(Offset(500f, 600f)) }

    var draggingPoint by remember { mutableStateOf<String?>(null) }

    val distance = sqrt((pointA.x - pointB.x).pow(2) + (pointA.y - pointB.y).pow(2))

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Arraste os pontos para verificar a distância entre eles.",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(12.dp))
        Text("Distância = ${distance.toInt()} unidades", style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(20.dp))

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    // detectTransformGestures for move + zoom
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale *= zoom
                        scale = scale.coerceIn(20f, 1000f)
                    }
                }
                .pointerInput(Unit) {
                    // detectDragGestures for moving points
                    detectDragGestures(
                        onDragStart = { pos ->
                            val localPos = (pos - offsetPoints) / (scale / 180f)
                            when {
                                (localPos - pointA).getDistance() < 70f -> draggingPoint = "A"
                                (localPos - pointB).getDistance() < 70f -> draggingPoint = "B"
                                else -> {
                                    null
                                }
                            }
                        },
                        onDrag = { change, dragAmount ->
                            when (draggingPoint) {
                                "A" -> pointA += dragAmount / (scale / 180f)
                                "B" -> pointB += dragAmount / (scale / 180f)
                                else -> {
                                    offsetPlain += dragAmount
                                }
                            }
                            change.consume()
                        },
                        onDragEnd = { draggingPoint = null },
                        onDragCancel = { draggingPoint = null }
                    )
                }
        ) {
            val w = size.width
            val h = size.height
            val origin = Offset(w / 2f + offsetPlain.x - 350f, h / 2f + offsetPlain.y + 600f)
            // ==== Draw Cartesian Grid ====
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
                // horizontal lines
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

            // axes
            drawLine(Color.Black, start = Offset(0f, origin.y), end = Offset(w, origin.y), strokeWidth = 3f)
            drawLine(Color.Black, start = Offset(origin.x, 0f), end = Offset(origin.x, h), strokeWidth = 3f)

            // ticks and labels
            val textPaint = Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 14.sp.toPx()
                isAntiAlias = true
            }

            // x-axis ticks
            var step = 1
            var x = origin.x + stepPx
            while (x < w) {
                drawLine(Color.Black, start = Offset(x, origin.y - 6f), end = Offset(x, origin.y + 6f), strokeWidth = 2f)
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
                drawLine(Color.Black, start = Offset(x, origin.y - 6f), end = Offset(x, origin.y + 6f), strokeWidth = 2f)
                val valX = step * unitsPerTick
                drawContext.canvas.nativeCanvas.drawText(valX.toString(), x - 10f, origin.y + 20f, textPaint)
                x -= stepPx
                step--
            }
            // y positive
            step = 1
            var y = origin.y - stepPx
            while (y > 0f) {
                drawLine(Color.Black, start = Offset(origin.x - 6f, y), end = Offset(origin.x + 6f, y), strokeWidth = 2f)
                val valY = step * unitsPerTick
                drawContext.canvas.nativeCanvas.drawText(valY.toString(), origin.x + 10f, y + 6f, textPaint)
                y -= stepPx
                step++
            }
            // y negative
            step = -1
            y = origin.y + stepPx
            while (y < h) {
                drawLine(Color.Black, start = Offset(origin.x - 6f, y), end = Offset(origin.x + 6f, y), strokeWidth = 2f)
                val valY = step * unitsPerTick
                drawContext.canvas.nativeCanvas.drawText(valY.toString(), origin.x + 10f, y + 6f, textPaint)
                y += stepPx
                step--
            }

            // ==== Draw the Distance Line + Points ====
            val pA = pointA * (scale / 180f) + offsetPoints
            val pB = pointB * (scale / 180f) + offsetPoints

            drawLine(Color.Blue, pA, pB, strokeWidth = 5f)
            drawCircle(Color.Red, radius = 30f, center = pA)
            drawCircle(Color.Green, radius = 30f, center = pB)
        }
    }
}
