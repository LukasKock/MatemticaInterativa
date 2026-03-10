package com.lk.matemticainterativa.ui.components.similarTriangles

import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt

fun calculateTrianglePoints(a1: Float, b1: Float, c1: Float): TrianglePoints {
    val pointA = Offset(0f, 0f)
    val pointB = Offset(c1, 0f)
    val xC = (b1 * b1 - a1 * a1 + c1 * c1) / (2f * c1)
    val yCSq = (b1 * b1 - xC * xC).coerceAtLeast(0f)
    val yC = -sqrt(yCSq)
    val pointC = Offset(xC, yC)

    return TrianglePoints(pointA, pointB, pointC)
}