package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset

fun pullVectorUp(vector: VectorPoints): VectorPoints{
    val offsetDown = -40f

    return VectorPoints(
        startPoint = Offset(vector.startPoint.x, vector.startPoint.y + offsetDown),
        endPoint = Offset(vector.endPoint.x, vector.endPoint.y + offsetDown)
    )
}