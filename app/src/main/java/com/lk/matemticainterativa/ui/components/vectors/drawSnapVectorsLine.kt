package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawSnapVectorsLine(vector1: VectorPoints, vector2: VectorPoints){
//    if(calculateDeltas(vector1, vector2))
    drawLine(
        color = Color.Red,
        start = vector1.startPoint,
        end = vector2.endPoint,
        strokeWidth = 5f,
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(20f, 10f), // on, off pattern
            phase = 0f
        )
    )
}