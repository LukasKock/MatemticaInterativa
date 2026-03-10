package com.lk.matemticainterativa.ui.components.similarTriangles

import kotlin.math.abs

fun areTrianglesAligned(trianglePoints1: TrianglePoints, trianglePoints2: TrianglePoints): Boolean {
    val tolerance = 45f
    return abs(trianglePoints1.pA.x - trianglePoints2.pA.x) +
            abs(trianglePoints1.pA.y - trianglePoints2.pA.y) +

            abs(trianglePoints1.pB.x - trianglePoints2.pB.x) +
            abs(trianglePoints1.pB.y - trianglePoints2.pB.y) +

            abs(trianglePoints1.pC.x - trianglePoints2.pC.x) +
            abs(trianglePoints1.pC.y - trianglePoints2.pC.y) < tolerance
}