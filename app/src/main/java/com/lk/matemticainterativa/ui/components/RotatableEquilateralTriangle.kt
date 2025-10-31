@file:OptIn(ExperimentalComposeUiApi::class)

package com.lk.matemticainterativa.ui.components

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.*

@Composable
fun RotatableEquilateralTriangle() {
    var rotation by remember { mutableStateOf(0f) }
    var tilt by remember { mutableStateOf(0f) }
    var scale by remember { mutableStateOf(1f) } // <— zoom level

    // Detect 2-finger gestures (rotation, zoom, pan)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, gestureZoom, gestureRotate ->
                    rotation += gestureRotate
                    scale = (scale * gestureZoom).coerceIn(0.5f, 3f)
                    tilt = (tilt + pan.y / 10f)/*.coerceIn(-60f, 60f)*/
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = size.center
            val triangleSize = min(size.width, size.height) / 3f * scale

            // Define triangle (equilateral)
            val p1 = Offset(center.x, center.y - triangleSize)
            val p2 = Offset(
                center.x - triangleSize * sin(Math.toRadians(60.0)).toFloat(),
                center.y + triangleSize / 2f
            )
            val p3 = Offset(
                center.x + triangleSize * sin(Math.toRadians(60.0)).toFloat(),
                center.y + triangleSize / 2f
            )

            // Apply fake 3D tilt (scaleY)
            val tiltRad = Math.toRadians(tilt.toDouble())
            val scaleY = cos(tiltRad).toFloat()

            // Apply rotation and vertical squish
            val rotationRad = Math.toRadians(rotation.toDouble())
            fun transform(p: Offset): Offset {
                val dx = p.x - center.x
                val dy = (p.y - center.y) * scaleY
                val x = dx * cos(rotationRad) - dy * sin(rotationRad)
                val y = dx * sin(rotationRad) + dy * cos(rotationRad)
                return Offset((center.x + x).toFloat(), (center.y + y).toFloat())
            }

            val rp1 = transform(p1)
            val rp2 = transform(p2)
            val rp3 = transform(p3)

            // Triangle path
            val path = Path().apply {
                moveTo(rp1.x, rp1.y)
                lineTo(rp2.x, rp2.y)
                lineTo(rp3.x, rp3.y)
                close()
            }

            // Fill with gradient color
            drawPath(
                path = path,
                brush = Brush.linearGradient(
                    listOf(Color.Green, Color.Yellow),
                    start = rp2,
                    end = rp3
                ),
                style = Fill
            )

            // Outline
            drawPath(path, color = Color.Black, style = Stroke(width = 3f))

            // Side lengths
            val a = (rp2 - rp3).getDistance()
            val b = (rp1 - rp3).getDistance()
            val c = (rp1 - rp2).getDistance()

            fun drawLabel(text: String, position: Offset) {
                drawContext.canvas.nativeCanvas.drawText(
                    text,
                    position.x,
                    position.y,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 36f * scale
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }

            // Label sides
            drawLabel("a=${a.roundToInt()}", (rp2 + rp3) / 2f)
            drawLabel("b=${b.roundToInt()}", (rp1 + rp3) / 2f)
            drawLabel("c=${c.roundToInt()}", (rp1 + rp2) / 2f)

            // Angles using cosine law
            val angleA = Math.toDegrees(
                acos(((b * b + c * c - a * a) / (2 * b * c)).toDouble())
            ).toFloat()
            val angleB = Math.toDegrees(
                acos(((a * a + c * c - b * b) / (2 * a * c)).toDouble())
            ).toFloat()
            val angleC = 180f - angleA - angleB

            // Label angles
            drawLabel("${angleA.roundToInt()}°", rp1 + Offset(20f, -10f))
            drawLabel("${angleB.roundToInt()}°", rp2 + Offset(-60f, 30f))
            drawLabel("${angleC.roundToInt()}°", rp3 + Offset(60f, 30f))
        }
    }
}
