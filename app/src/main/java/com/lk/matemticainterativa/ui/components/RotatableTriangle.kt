@file:OptIn(ExperimentalComposeUiApi::class)

package com.lk.matemticainterativa.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.graphics.Paint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

@Composable
fun RotatableTriangle(
    a: Float, // side opposite vertex A (BC)
    b: Float, // side opposite vertex B (AC)
    c: Float  // side opposite vertex C (AB)
) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if(isDark)  Color(0xFF121212) else Color(0xFFFFFFFF)
    val triangleOutlineColor = if (isDark) Color(0xFFB0BEC5) else Color.Black
    val textColor = if (isDark) Color(0xFFE0E0E0) else Color.Black
    val triangle1Color = if(isDark) Color(0xFF0B3B4B) else Color.Cyan
    val triangle2Color = if(isDark) Color(0xFF483E07) else Color.Yellow

    val validTriangle = remember(a, b, c) {
        (a + b > c) && (a + c > b) && (b + c > a)
    }

    var rotation by rememberSaveable { mutableFloatStateOf(0f) }
    var tilt by rememberSaveable { mutableFloatStateOf(-1f) }
    var scale by rememberSaveable { mutableFloatStateOf(1f) }

    // Offset isn't automatically saveable, so we need a custom Saver
    val offsetSaver = listSaver(
        save = { listOf(it.x, it.y) },
        restore = { Offset(it[0], it[1]) }
    )
    // This state will now hold the accumulated pan/drag offset
    var panOffset by rememberSaveable(stateSaver = offsetSaver) { mutableStateOf(Offset.Zero) }

    Column(modifier = Modifier.fillMaxSize()
        .background(color = backgroundColor)) {
        Spacer(modifier = Modifier.padding(48.dp))

//test        ControlSlider("teste", tilt, { tilt = it }, -1f..1f, "%.2f")
        AnimatedFloatSwitch ("\"Flip\""){ tilt = it }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, gestureZoom, gestureRotate ->
                        rotation += gestureRotate
                        scale = (scale * gestureZoom).coerceIn(0.3f, 6f)
                        panOffset += pan // Accumulate the pan gesture
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasCenter = size.center

                if (!validTriangle) {
                    val paint = Paint().apply {
                        color = android.graphics.Color.RED
                        textSize = 48f
                        textAlign = Paint.Align.CENTER
                    }
                    drawContext.canvas.nativeCanvas.drawText(
                        "Invalid sides: cannot form a triangle",
                        canvasCenter.x,
                        canvasCenter.y,
                        paint
                    )
                    return@Canvas
                }

                // --- Build triangle in local model coordinates (ALWAYS THE SAME) ---
                val pointA = Offset(0f, 0f)
                val pointB = Offset(c, 0f)
                val xC = (b * b - a * a + c * c) / (2f * c)
                val yCSq = (b * b - xC * xC).coerceAtLeast(0f)
                val yC = -sqrt(yCSq) // Use negative so the triangle is initially upright
                val pointC = Offset(xC, yC)

                // Compute centroid of the ORIGINAL, UNMODIFIED model triangle
                val centroidModel = (pointA + pointB + pointC) / 3f

                // --- Transformation Pipeline ---
                val rotationRad = Math.toRadians(rotation.toDouble())

                fun transform(p: Offset): Offset {
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

                val pA = transform(pointA)
                val pB = transform(pointB)
                val pC = transform(pointC)

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
                        color = triangle1Color)

                } else{
                    drawPath(
                        path = path,
                        color = triangle2Color)
                }


                drawPath(path = path, color = triangleOutlineColor, style = Stroke(width = 3f))

                // ... (The rest of your label and angle drawing code can stay here without changes)
                val baseTextSize = 28f
                val paint = Paint().apply {
                    color = textColor.toArgb()
                    textSize = (baseTextSize * scale).coerceAtLeast(12f)
                    textAlign = Paint.Align.CENTER
                }

                fun drawSideLabel(text: String, p1: Offset, p2: Offset, dyOffset: Float = 0f) {
                    val mid = (p1 + p2) / 2f
                    drawContext.canvas.nativeCanvas.drawText(
                        text,
                        mid.x,
                        mid.y + dyOffset * scale,
                        paint
                    )
                }

                drawSideLabel("a=${a.roundToInt()}", pB, pC, 15f)
                drawSideLabel("b=${b.roundToInt()}", pA, pC, 15f)
                drawSideLabel("c=${c.roundToInt()}", pA, pB, -15f)

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
                        size = androidx.compose.ui.geometry.Size(radius * 2, verticalRadius * 2),
                        style = Stroke(width = 2f)
                    )
                }

                // Helper extension to normalize an Offset vector


                // Draw the small arcs near vertices
                val arcRadius = 40f * scale
                drawAngleArc(pA, pB, pC, angleA, arcRadius)
                drawAngleArc(pB, pA, pC, angleB, arcRadius)
                drawAngleArc(pC, pA, pB, angleC, arcRadius)

                // --- Draw the angle labels INSIDE the triangle, following its rotation ---
                val newCentroid = (pA + pB + pC) / 3f

                fun Offset.moveToward(target: Offset, fraction: Float): Offset {
                    return this + (target - this) * fraction
                }

                val labelFraction = 0.25f // how deep inside the triangle the label goes

                val labelPosA = pA.moveToward(newCentroid, labelFraction)
                val labelPosB = pB.moveToward(newCentroid, labelFraction)
                val labelPosC = pC.moveToward(newCentroid, labelFraction)


                drawContext.canvas.nativeCanvas.drawText(
                    "${angleA.roundToInt()}°",
                    labelPosA.x,
                    labelPosA.y,
                    paint
                )
                drawContext.canvas.nativeCanvas.drawText(
                    "${angleB.roundToInt()}°",
                    labelPosB.x,
                    labelPosB.y,
                    paint
                )
                drawContext.canvas.nativeCanvas.drawText(
                    "${angleC.roundToInt()}°",
                    labelPosC.x,
                    labelPosC.y,
                    paint
                )
            }
        }
    }
}

/**
 * A helper composable to reduce boilerplate for sliders.
 */
@Composable
fun ControlSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>,
    format: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("$label:")
            Text(format.format(value))
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
        )
    }
}
@Composable
fun AnimatedFloatSwitch(text: String, onTiltChange: (Float) -> Unit) {

    var toggled by remember { mutableStateOf(false) }

    // Animate between -1f and 1f when switch changes
    val animatedFloat by animateFloatAsState(
        targetValue = if (toggled) 1f else -1f,
        animationSpec = tween(
            durationMillis = 500, // 2 seconds
            easing = LinearEasing
        ),
        label = "FloatAnimation"
    )
    // Whenever the animation updates, assign it to your tilt
    LaunchedEffect(animatedFloat) {
        onTiltChange(animatedFloat)
    }

    // Theme colors
    val colorScheme = MaterialTheme.colorScheme
    val isDark = isSystemInDarkTheme()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            ,verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = if(isDark)colorScheme.secondaryContainer
            else Color.Black,
        )
        Spacer(Modifier.weight(1f))
        Switch(
            checked = toggled,
            onCheckedChange = { toggled = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorScheme.onPrimary,
                checkedTrackColor = colorScheme.primary,
                uncheckedThumbColor = colorScheme.onSurface,
                uncheckedTrackColor = colorScheme.outlineVariant
            )
        )
    }
}