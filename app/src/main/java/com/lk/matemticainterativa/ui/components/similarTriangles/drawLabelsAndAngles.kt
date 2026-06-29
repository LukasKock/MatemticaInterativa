package com.lk.matemticainterativa.ui.components.similarTriangles

import android.graphics.Paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.roundToInt

fun DrawScope.drawLabelsAndAngles(textColor: Color, scale: Float, tilt: Float, a: Float, b: Float, c: Float,
                                  showSideA: Boolean = true,
                                  showSideB: Boolean = true,
                                  showSideC: Boolean = true,
                                  showAngleA: Boolean = true,
                                  showAngleB: Boolean = true,
                                  showAngleC: Boolean = true,
                                  pA: Offset, pB: Offset, pC: Offset, dpi: Float){
    val baseTextSize = 32f
    val paint = Paint().apply {
        color = textColor.toArgb()
        textSize = baseTextSize
        textAlign = Paint.Align.CENTER
    }

    fun drawSideLabel(text: String, p1: Offset, p2: Offset, dyOffset: Float = 0f, paint: Paint) {
        val mid = (p1 + p2) / 2f
        drawContext.canvas.nativeCanvas.drawText(
            String.format("%s cm", text),
            mid.x,
            mid.y + dyOffset * scale,
            paint
        )
    }
    if(showSideA) drawSideLabel("${((pC - pB).getDistance()/dpi * 2.54f).roundToInt()}", pB, pC, 15f, paint)
    if(showSideB) drawSideLabel("${((pA - pC).getDistance()/dpi * 2.54f).roundToInt()}", pA, pC, 15f, paint)
    if(showSideC) drawSideLabel("${((pB - pA).getDistance()/dpi * 2.54f).roundToInt()}", pA, pB, -15f, paint)

    fun angleFromSides(opposite: Float, side1: Float, side2: Float): Float {
        val cosVal = ((side1 * side1 + side2 * side2 - opposite * opposite) /
                (2f * side1 * side2)).coerceIn(-1f, 1f)
        return Math.toDegrees(acos(cosVal.toDouble())).toFloat()
    }

    val angleA = angleFromSides(a, b, c)
    val angleB = angleFromSides(b, a, c)
    val angleC = 180f - angleA - angleB


    // --- Draw arcs for each angle ---
    fun Offset.normalize(): Offset {
        val len = hypot(x, y)
        return if (len == 0f) this else Offset(x / len, y / len)
    }

    fun drawAngleArc(center: Offset, p1: Offset, p2: Offset, angleDeg: Float, radius: Float) {
        val v1 = (p1 - center).normalize()
        val v2 = (p2 - center).normalize()

        // Compute the direction of the first vector
        val startAngle = Math.toDegrees(atan2(v1.y, v1.x).toDouble()).toFloat()
        val sweepAngle = if ((v1.x * v2.y - v1.y * v2.x) < 0) -angleDeg else angleDeg

        // Make the arc elliptical according to the tilt factor
        val verticalRadius = radius * abs(tilt)

        drawArc(
            color = textColor,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - verticalRadius),
            size = Size(radius * 2, verticalRadius * 2),
            style = Stroke(width = 2f)
        )
    }

    // Helper extension to normalize an Offset vector


    // Draw the small arcs near vertices
    val arcRadius = 40f * scale
    if(showAngleA) drawAngleArc(pA, pB, pC, angleA, arcRadius)
    if(showAngleB) drawAngleArc(pB, pA, pC, angleB, arcRadius)
    if(showAngleC) drawAngleArc(pC, pA, pB, angleC, arcRadius)

    // --- Draw the angle labels INSIDE the triangle, following its rotation ---
    val newCentroid = (pA + pB + pC) / 3f

    fun Offset.moveToward(target: Offset, fraction: Float): Offset {
        return this + (target - this) * fraction
    }

    val labelFraction = 0.25f // how deep inside the triangle the label goes

    val labelPosA = pA.moveToward(newCentroid, labelFraction)
    val labelPosB = pB.moveToward(newCentroid, labelFraction)
    val labelPosC = pC.moveToward(newCentroid, labelFraction)


    if(showAngleA) drawContext.canvas.nativeCanvas.drawText(
        "${angleA.roundToInt()}°",
        labelPosA.x,
        labelPosA.y,
        paint
    )
    if(showAngleB) drawContext.canvas.nativeCanvas.drawText(
        "${angleB.roundToInt()}°",
        labelPosB.x,
        labelPosB.y,
        paint
    )
    if(showAngleC) drawContext.canvas.nativeCanvas.drawText(
        "${angleC.roundToInt()}°",
        labelPosC.x,
        labelPosC.y,
        paint
    )
}