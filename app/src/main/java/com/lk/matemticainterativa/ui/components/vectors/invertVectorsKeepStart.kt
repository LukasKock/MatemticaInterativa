package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset


fun invertVectorKeepStart(vector: VectorPoints): VectorPoints {
    val deltaX = vector.endPoint.x - vector.startPoint.x
    val deltaY = vector.endPoint.y - vector.startPoint.y

    return VectorPoints(
        startPoint = vector.startPoint,
        endPoint = Offset(vector.startPoint.x - deltaX, vector.startPoint.y - deltaY))
}