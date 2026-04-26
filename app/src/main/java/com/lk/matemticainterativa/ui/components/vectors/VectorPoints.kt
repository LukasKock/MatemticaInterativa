package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset
import kotlin.math.sqrt

data class VectorPoints(val startPoint: Offset, val endPoint: Offset){
    fun moduleSquared(): Float {
        val v = endPoint - startPoint
        return v.x * v.x + v.y * v.y
    }
    fun length(): Float = sqrt(moduleSquared())

    fun direction(): Offset {
        val v = endPoint - startPoint
        val len = length()
        return if (len != 0f) Offset(v.x / len, v.y / len) else Offset.Zero
    }
}