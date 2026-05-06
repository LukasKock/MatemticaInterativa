package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.runtime.Composable
import kotlin.math.abs

@Composable
fun isVectorSumCompleted(vector1: VectorPoints,
                         vector2: VectorPoints,
                         resultVector: VectorPoints): Boolean {
    val dx12 = vector1.endPoint.x - vector2.startPoint.x
    val dy12 = vector1.endPoint.y - vector2.startPoint.y

    val dx2r = vector2.endPoint.x - resultVector.endPoint.x
    val dy2r = vector2.endPoint.y - resultVector.endPoint.y

    val dx1r = vector1.startPoint.x - resultVector.startPoint.x
    val dy1r = vector1.startPoint.y - resultVector.startPoint.y


    val threshold = 3f

    return abs(dx12) < threshold && abs(dy12) < threshold &&
            abs(dx2r) < threshold && abs(dy2r) < threshold &&
            abs(dx1r) < threshold && abs(dy1r) < threshold
}