package com.lk.matemticainterativa.ui.components.cartesianPlane

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

fun DrawScope.drawAxis(
    start: Offset,
    end: Offset,
    color: Color = Color.Black,
    strokeWidth: Float = 4f,
    arrowLength: Float = 25f,
    arrowAngle: Float = 30f
) {


    // main line
    drawLine(
        color = color,
        start = start,
        end = end,
        strokeWidth = strokeWidth
    )

    // direction angle
    val angle = atan2(
        y = end.y - start.y,
        x = end.x - start.x
    )

    val angleRad = Math.toRadians(arrowAngle.toDouble()).toFloat()

    // left side
    val p1 = Offset(
        x = end.x - arrowLength * cos(angle - angleRad),
        y = end.y - arrowLength * sin(angle - angleRad)
    )

    // right side
    val p2 = Offset(
        x = end.x - arrowLength * cos(angle + angleRad),
        y = end.y - arrowLength * sin(angle + angleRad)
    )

    drawLine(
        color = color,
        start = end,
        end = p1,
        strokeWidth = strokeWidth
    )

    drawLine(
        color = color,
        start = end,
        end = p2,
        strokeWidth = strokeWidth
    )
}