package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawVector(vector: VectorPoints, vectorColor: Color, textColor: Color, isVectorSelected: Int?, name: String) {
    //Draw thicker line if vector is selected
    if(isVectorSelected != null){
        drawLine(
            color = vectorColor.copy(alpha = 0.4f),
            start = vector.startPoint,
            end = vector.endPoint,
            strokeWidth = 12f
        )
    }
    //draw vector's line
    drawLine(
        color = vectorColor,
        start = vector.startPoint,
        end = vector.endPoint,
        strokeWidth = 6f
    )
    //Draw vector's letter
    val paint = android.graphics.Paint().apply {
        color = textColor.toArgb()
        textSize = 50f
        textAlign = android.graphics.Paint.Align.CENTER
    }
    val mid = (vector.startPoint + vector.endPoint) / 2f
    drawContext.canvas.nativeCanvas.drawText(
        name,
        mid.x,
        mid.y - 32f,
        paint
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
        drawLine(vectorColor.copy(alpha = 0.4f), vector.endPoint, arrowPoint1, strokeWidth = 8f)
        drawLine(vectorColor.copy(alpha = 0.4f), vector.endPoint, arrowPoint2, strokeWidth = 8f)
    }
    drawLine(vectorColor, vector.endPoint, arrowPoint1, strokeWidth = 4f)
    drawLine(vectorColor, vector.endPoint, arrowPoint2, strokeWidth = 4f)
}