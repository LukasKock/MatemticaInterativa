package com.lk.matemticainterativa.ui.components.similarTriangles

import androidx.compose.ui.geometry.Offset

fun isPointInTriangle(p: Offset, a: Offset, b: Offset, c: Offset): Boolean {
    val v0 = c - a
    val v1 = b - a
    val v2 = p - a

    val dot00 = v0.x * v0.x + v0.y * v0.y
    val dot01 = v0.x * v1.x + v0.y * v1.y
    val dot02 = v0.x * v2.x + v0.y * v2.y
    val dot11 = v1.x * v1.x + v1.y * v1.y
    val dot12 = v1.x * v2.x + v1.y * v2.y

    val invDenom = 1f / (dot00 * dot11 - dot01 * dot01)
    val u = (dot11 * dot02 - dot01 * dot12) * invDenom
    val v = (dot00 * dot12 - dot01 * dot02) * invDenom

    return (u >= 0) && (v >= 0) && (u + v <= 1)
}