package com.lk.matemticainterativa.ui.components.vectors

import kotlin.math.abs

fun calculateDeltasFloat(vector1: VectorPoints, vector2: VectorPoints): Boolean {
    val deltaX = vector1.startPoint.x - vector2.endPoint.x
    val deltaY = vector1.startPoint.y - vector2.endPoint.y


    val threshold = 70f

    return abs(deltaX) < threshold && abs(deltaY) < threshold
}