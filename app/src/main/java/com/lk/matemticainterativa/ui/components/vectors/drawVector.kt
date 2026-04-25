package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawVector(vector: VectorPoints, color: Color, isVectorSelected: Int?) {
    if(isVectorSelected != null){
        drawLine(
            color = color.copy(alpha = 0.4f),
            start = vector.startPoint,
            end = vector.endPoint,
            strokeWidth = 12f
        )
    }
    drawLine(
        color = color,
        start = vector.startPoint,
        end = vector.endPoint,
        strokeWidth = 6f
    )

    // Arrow head
    val angle = atan2(vector.endPoint.y - vector.startPoint.y, vector.endPoint.x - vector.startPoint.x)

    val arrowLength = 30f
    val angleOffset = Math.PI / 6

    val arrowPoint1 = Offset(
        (vector.endPoint.x - arrowLength * cos(angle - angleOffset)).toFloat(),
        (vector.endPoint.y - arrowLength * sin(angle - angleOffset)).toFloat()
    )

    val arrowPoint2 = Offset(
        (vector.endPoint.x - arrowLength * cos(angle + angleOffset)).toFloat(),
        (vector.endPoint.y - arrowLength * sin(angle + angleOffset)).toFloat()
    )

    if(isVectorSelected != null){
        drawLine(color.copy(alpha = 0.4f), vector.endPoint, arrowPoint1, strokeWidth = 8f)
        drawLine(color.copy(alpha = 0.4f), vector.endPoint, arrowPoint2, strokeWidth = 8f)
    }
    drawLine(color, vector.endPoint, arrowPoint1, strokeWidth = 4f)
    drawLine(color, vector.endPoint, arrowPoint2, strokeWidth = 4f)
}