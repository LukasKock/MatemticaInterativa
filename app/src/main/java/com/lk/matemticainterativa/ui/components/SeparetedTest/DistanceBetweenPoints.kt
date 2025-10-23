package com.lk.matemticainterativa.ui.components.SeparetedTest

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
fun DistanceBetweenPoints(
    offset: Offset,
    scale: Float
) {
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
                            val Atest = pos - (pointA + offset)
                            val atestx = Atest.x
                            val atesty = Atest.y
                            val Btest = pos - (pointB + offset)
                            val btestx = Btest.x
                            val btesty = Btest.y
                            draggingPoint = when {
                                (pos - (pointA + offset)).getDistance() < 60f -> "A"
                                (pos - (pointB + offset)).getDistance() < 60f -> "B"
                                else -> null
                            }
                        },
                        onDrag = { change, dragAmount ->
                            when (draggingPoint) {
                                "A" -> pointA += dragAmount / (scale / 180f)
                                "B" -> pointB += dragAmount / (scale / 180f)
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
                val pA = pointA * (scale / 180f) + offset
                val pB = pointB * (scale / 180f) + offset

                drawLine(Color.Blue, pA, pB, strokeWidth = 5f)
                drawCircle(Color.Red, radius = 30f, center = pA)
                drawCircle(Color.Green, radius = 30f, center = pB)
            }
        }
    }
}