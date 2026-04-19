package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.geometry.Offset

fun distanceToSegment(p: Offset, vector: VectorPoints): Float {
    val ab = vector.endPoint - vector.startPoint
    val ap = p - vector.startPoint

    val abLengthSquared = ab.x * ab.x + ab.y * ab.y

    // Avoid division by zero (if A == B)
    if (abLengthSquared == 0f) return (p - vector.startPoint).getDistance()

    // Projection factor (0 = A, 1 = B)
    val t = (ap.x * ab.x + ap.y * ab.y) / abLengthSquared

    // Clamp to segment
    val tClamped = t.coerceIn(0f, 1f)

    // Closest point on segment
    val closest = Offset(
        vector.startPoint.x + ab.x * tClamped,
        vector.startPoint.y + ab.y * tClamped
    )

    return (p - closest).getDistance()
}