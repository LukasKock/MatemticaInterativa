package com.lk.matemticainterativa.ui.components.similarTriangles

import androidx.compose.ui.geometry.Offset
import kotlin.math.cos
import kotlin.math.sin

fun transform(canvasCenter: Offset, p: Offset, centroidModel: Offset, scale: Float, tilt: Float, rotationRad: Double, panOffset: Offset): Offset {
    // 1. Translate point so its centroid is at the origin
    val centeredPoint = (p - centroidModel)

    // 2. Apply transformations: scale, tilt (squish), and rotate
    val scaledX = centeredPoint.x * scale
    var scaledY = centeredPoint.y * scale
    scaledY *= tilt // Apply vertical squish for tilt effect

    val rotatedX = scaledX * cos(rotationRad) - scaledY * sin(rotationRad)
    val rotatedY = scaledX * sin(rotationRad) + scaledY * cos(rotationRad)

    // 3. Translate to canvas center AND apply the accumulated pan offset
    return Offset(
        (canvasCenter.x + rotatedX).toFloat() + panOffset.x,
        (canvasCenter.y + rotatedY).toFloat() + panOffset.y
    )
}
