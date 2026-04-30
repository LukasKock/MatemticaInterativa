package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset
import kotlin.math.abs

fun calculateDeltaStartEnd(vector1: VectorPoints, vector2: VectorPoints): Offset {
    return vector2.startPoint - vector1.endPoint
}

fun calculateDeltaStartStart(vector1: VectorPoints, vector2: VectorPoints): Offset {
    return vector2.startPoint - vector1.startPoint
}

fun calculateDeltaStartEndFloat(vector1: VectorPoints, vector2: VectorPoints): Boolean {
    val deltaX = vector1.startPoint.x - vector2.endPoint.x
    val deltaY = vector1.startPoint.y - vector2.endPoint.y


    val threshold = 70f

    return abs(deltaX) < threshold && abs(deltaY) < threshold
}

fun calculateDeltaStartStartFloat(vector1: VectorPoints, vector2: VectorPoints): Boolean {
    val deltaX = vector1.startPoint.x - vector2.startPoint.x
    val deltaY = vector1.startPoint.y - vector2.startPoint.y


    val threshold = 70f

    return abs(deltaX) < threshold && abs(deltaY) < threshold
}