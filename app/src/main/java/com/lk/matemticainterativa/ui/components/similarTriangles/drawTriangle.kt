package com.lk.matemticainterativa.ui.components.similarTriangles

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

fun DrawScope.drawTriangle(pA: Offset, pB: Offset, pC: Offset, color1: Color, color2: Color, colorLine: Color,
                           colorLineSelected: Color, isTriangleSelected: Boolean = false){
    // --- Drawing Code (remains mostly the same) ---
    val path = Path().apply {
        moveTo(pA.x, pA.y)
        lineTo(pB.x, pB.y)
        lineTo(pC.x, pC.y)
        close()
    }
    val cross = (pB.x - pA.x) * (pC.y - pA.y) - (pB.y - pA.y) * (pC.x - pA.x)
    if(cross > 0){
        drawPath(
            path = path,
            color = color1)

    } else{
        drawPath(
            path = path,
            color = color2)
    }

    if(isTriangleSelected){
        drawPath(path = path, color = colorLineSelected, style = Stroke(width = 6f))
    } else {
        drawPath(path = path, color = colorLine, style = Stroke(width = 3f))
    }
}