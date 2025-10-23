package com.lk.matemticainterativa.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PolygonTransformWithSlider() {
    var center by remember { mutableStateOf(Offset(400f, 600f)) } // initial center
    var scale by remember { mutableFloatStateOf(1f) } // controlled by slider
    var rotation by remember { mutableFloatStateOf(0f) } // optional rotation

    Column(modifier = Modifier.fillMaxSize()) {
        // ✅ Slider for scaling
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Escala: %.2f".format(scale))
        }
        Slider(
            value = scale,
            onValueChange = { scale = it },
            valueRange = 0.5f..4f, // min and max zoom
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // ✅ Triangle drawing with pan gestures
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, _, rotate ->
                            val rotateDelta = kotlin.math.abs(rotate)

                            if (rotateDelta > 0.01f) {
                                // ✅ Rotate only
                                rotation += rotate / 6f
                            } else {
                                // ✅ Pan only (when not rotating)
                                center += pan
                            }
                        }
                    }
            ) {
                // Polygon creation
                val polygon = regularPolygon(6, 200f)
                // Apply transformations (scale + pan only)
                val path = Path().apply {
                    val transformed = polygon.map { point ->
                        // Scale first
                        var p = point * scale

                        // ✅ Rotate around the center, not (0,0)
                        val dx = p.x
                        val dy = p.y
                        val x = dx * cos(rotation) - dy * sin(rotation)
                        val y = dx * sin(rotation) + dy * cos(rotation)
                        p = Offset(x, y)

                        // ✅ Then translate to the chosen center
                        p + center
                    }
                    moveTo(transformed[0].x, transformed[0].y)
                    transformed.drop(1).forEach {
                        lineTo(it.x, it.y)
                    }
                    close()
                }

                // Draw triangle
                drawPath(path, color = Color(0xFFF44336))
            }
        }
    }
}

fun regularPolygon(sides: Int, radius: Float): List<Offset> {
    require(sides >= 3) { "A polygon must have at least 3 sides" }

    val angleStep = (2 * Math.PI / sides).toFloat()

    return List(sides) { i ->
        val angle = -Math.PI / 2 + i * angleStep  // start pointing "up"
        Offset(
            x = radius * cos(angle).toFloat(),
            y = radius * sin(angle).toFloat()
        )
    }
}
