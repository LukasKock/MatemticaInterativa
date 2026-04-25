package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset


fun invertVectorKeepStart(vector: VectorPoints): VectorPoints {
    val deltax = vector.endPoint.x - vector.startPoint.x
    val deltay = vector.endPoint.y - vector.startPoint.y

    return VectorPoints(
        startPoint = vector.startPoint,
        endPoint = Offset(vector.startPoint.x - deltax, vector.startPoint.y - deltay))
}