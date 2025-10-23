package com.lk.matemticainterativa.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun DistanceBetweenPoints() {
    var pointA by remember { mutableStateOf(Offset(200f, 200f)) }
    var pointB by remember { mutableStateOf(Offset(500f, 600f)) }

    val distance = sqrt(
        (pointA.x - pointB.x).pow(2) + (pointA.y - pointB.y).pow(2)
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    var draggingPoint: String? = null  // "A", "B", or null

                    detectDragGestures(
                        onDragStart = { pos ->
                            draggingPoint = when {
                                (pos - pointA).getDistance() < 60f -> "A"
                                (pos - pointB).getDistance() < 60f -> "B"
                                else -> null
                            }
                        },
                        onDrag = { change, dragAmount ->
                            when (draggingPoint) {
                                "A" -> pointA += dragAmount
                                "B" -> pointB += dragAmount
                            }
                            change.consume() // prevent other gestures
                        },
                        onDragEnd = {
                            draggingPoint = null
                        },
                        onDragCancel = {
                            draggingPoint = null
                        }
                    )
                }

            ) {
                // Draw line between points
                drawLine(Color.Blue, pointA, pointB, strokeWidth = 5f)

                // Draw points
                drawCircle(Color.Red, radius = 30f, center = pointA)
                drawCircle(Color.Green, radius = 30f, center = pointB)
            }
        }
    }
}