package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset

fun calculateDelta(vector1: VectorPoints, vector2: VectorPoints): Offset {
    return vector2.startPoint - vector1.endPoint
}
