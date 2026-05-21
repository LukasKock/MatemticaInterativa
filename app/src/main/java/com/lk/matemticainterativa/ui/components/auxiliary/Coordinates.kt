package com.lk.matemticainterativa.ui.components.auxiliary

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

fun cartesianToCanvas(
    coordinates: Offset,
    screenSize: Size,
    planeLimit: Float = 10f
): Offset {

    val centerX = screenSize.width / 2f
    val centerY = screenSize.height / 2f

    // Keep X and Y scale equal
    val scale = minOf(
        screenSize.width,
        screenSize.height
    ) / (planeLimit * 2f)

    return Offset(
        x = centerX + coordinates.x * scale,
        y = centerY - coordinates.y * scale
    )
}