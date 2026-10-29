package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize

fun calculateConstrainedBounds(vector: VectorPoints, dragAmount: Offset, canvasSize: IntSize): Offset {
    val canvasWidth = canvasSize.width.toFloat()
    val canvasHeight = canvasSize.height.toFloat()
    val minX = -minOf(
        vector.startPoint.x,
        vector.endPoint.x
    )
    val maxX = canvasWidth - maxOf(
        vector.startPoint.x,
        vector.endPoint.x
    )

    val minY = -minOf(
        vector.startPoint.y,
        vector.endPoint.y
    )
    val maxY = canvasHeight - maxOf(
        vector.startPoint.y,
        vector.endPoint.y
    )
    return Offset(
        x = dragAmount.x.coerceIn(minX, maxX),
        y = dragAmount.y.coerceIn(minY, maxY)
    )
}