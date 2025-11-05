@file:OptIn(ExperimentalComposeUiApi::class)

package com.lk.matemticainterativa.ui.components.triangle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.graphics.Paint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

@Composable
fun RotatableTriangle(
    a1: Float, // side opposite vertex A (BC)
    b1: Float, // side opposite vertex B (AC)
    c1: Float,  // side opposite vertex C (AB)
    a2: Float, // side opposite vertex A (BC)
    b2: Float, // side opposite vertex B (AC)
    c2: Float  // side opposite vertex C (AB)

) {
    val isDark = isSystemInDarkTheme()
    val backgroundColor = if(isDark)  Color(0xFF121212) else Color(0xFFFFFFFF)
    val triangleOutlineColor = if (isDark) Color(0xFFB0BEC5) else Color.Black
    val triangleOutlineColorSelected = if(isDark) Color(0xFFDC3A0A) else Color.Red
    val textColor = if (isDark) Color(0xFFE0E0E0) else Color.Black
    val triangle1Color = if(isDark) Color(0xFF0B3B4B) else Color.Cyan
    val triangle2Color = if(isDark) Color(0xFF483E07) else Color.Yellow

    val validTriangle = remember(a1, b1, c1, a2, b2, c2) {
        (a1 + b1 > c1) && (a1 + c1 > b1) && (b1 + c1 > a1)
                ||
                (a2 + b2 > c2) && (a2 + c2 > b2) && (b2 + c2 > a2)
    }

    var rotation1 by rememberSaveable { mutableFloatStateOf(0f) }
    var rotation2 by rememberSaveable { mutableFloatStateOf(0f) }
    var tilt1 by rememberSaveable { mutableFloatStateOf(-1f) }
    var tilt2 by rememberSaveable { mutableFloatStateOf(-1f) }
    var scale1 by rememberSaveable { mutableFloatStateOf(1f) }
    var scale2 by rememberSaveable { mutableFloatStateOf(1f) }

    var isTriangle1Selected by rememberSaveable { mutableStateOf(false) }
    var isTriangle2Selected by rememberSaveable { mutableStateOf(false) }

    // Offset isn't automatically saveable, so we need a custom Saver
    val offsetSaver1 = listSaver(
        save = { listOf(it.x, it.y) },
        restore = { Offset(it[0], it[1]) }
    )
    val offsetSaver2 = listSaver(
        save = { listOf(it.x, it.y) },
        restore = { Offset(it[0], it[1]) }
    )
    // This state will now hold the accumulated pan/drag offset
    var panOffset1 by rememberSaveable(stateSaver = offsetSaver1) { mutableStateOf(Offset.Zero) }
    var panOffset2 by rememberSaveable(stateSaver = offsetSaver2) { mutableStateOf(Offset.Zero) }

    var pA1: Offset = Offset.Zero
    var pB1: Offset = Offset.Zero
    var pC1: Offset = Offset.Zero

    var pA2: Offset = Offset.Zero
    var pB2: Offset = Offset.Zero
    var pC2: Offset = Offset.Zero


    Column(modifier = Modifier.fillMaxSize()
        .background(color = backgroundColor)
    ) {
        Spacer(modifier = Modifier.padding(48.dp))

//test        ControlSlider("teste", tilt, { tilt = it }, -1f..1f, "%.2f")
        AnimatedFloatSwitch ("\"Flip\""){ tilt1 = it }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, gestureZoom, gestureRotate ->
                        if(isTriangle1Selected == true) {
                            rotation1 += gestureRotate
                            scale1 = (scale1 * gestureZoom).coerceIn(0.3f, 6f)
                            panOffset1 += pan // Accumulate the pan gesture
                        } else if(isTriangle2Selected == true){
                            rotation2 += gestureRotate
                            scale2 = (scale2 * gestureZoom).coerceIn(0.3f, 6f)
                            panOffset2 += pan // Accumulate the pan gesture
                        }else{
                            return@detectTransformGestures
                        }
                    }
                }
                .pointerInput(Unit){
                    detectTapGestures { tapOffset ->
                        if (isPointInTriangle(tapOffset, pA1, pB1, pC1)) {
                            println("Tapped INSIDE the triangle!")
                            isTriangle1Selected = true
                            // e.g. set a state variable: triangleSelected = true
                        } else if(isPointInTriangle(tapOffset, pA2, pB2, pC2)){
                            println("Tapped OUTSIDE the triangle.")
                            // triangleSelected = false
                            isTriangle2Selected = true
                        } else{
                            isTriangle1Selected = false
                            isTriangle2Selected = false
                        }
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


                val triangle1 = calculateTrianglePoints(a1,b1,c1)
                val (pointA1, pointB1, pointC1) = triangle1

                val triangle2 = calculateTrianglePoints(a2,b2,c2)
                val (pointA2, pointB2, pointC2) = triangle2

                // Compute centroid of the ORIGINAL, UNMODIFIED model triangle
                val centroidModel1 = (pointA1 + pointB1 + pointC1) / 3f
                val centroidModel2 = (pointA2 + pointB2 + pointC2) / 3f

                // --- Transformation Pipeline ---
                val rotationRad1 = Math.toRadians(rotation1.toDouble())
                val rotationRad2 = Math.toRadians(rotation2.toDouble())

                fun transform(p: Offset, centroidModel: Offset, scale: Float, tilt: Float, rotationRad: Double, panOffset: Offset): Offset {
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

                pA1 = transform(pointA1, centroidModel1, scale1, tilt1, rotationRad1, panOffset1)
                pB1 = transform(pointB1, centroidModel1, scale1, tilt1, rotationRad1, panOffset1)
                pC1 = transform(pointC1, centroidModel1, scale1, tilt1, rotationRad1, panOffset1)

                pA2 = transform(pointA2, centroidModel2, scale2, tilt2, rotationRad2, panOffset2)
                pB2 = transform(pointB2, centroidModel2, scale2, tilt2, rotationRad2, panOffset2)
                pC2 = transform(pointC2, centroidModel2, scale2, tilt2, rotationRad2, panOffset2)




                drawTriangle(pA1,pB1,pC1,triangle1Color,triangle2Color,triangleOutlineColor,
                    triangleOutlineColorSelected, isTriangle1Selected)
                drawTriangle(pA2,pB2,pC2,triangle2Color,triangle1Color,triangleOutlineColor,
                    triangleOutlineColorSelected, isTriangle2Selected)
                // ... (The rest of your label and angle drawing code can stay here without changes)
                val baseTextSize = 28f
                val paint1 = Paint().apply {
                    color = textColor.toArgb()
                    textSize = (baseTextSize * scale1).coerceAtLeast(12f)
                    textAlign = Paint.Align.CENTER
                }

                fun drawSideLabel(text: String, p1: Offset, p2: Offset, dyOffset: Float = 0f, paint: Paint) {
                    val mid = (p1 + p2) / 2f
                    drawContext.canvas.nativeCanvas.drawText(
                        text,
                        mid.x,
                        mid.y + dyOffset * scale1,
                        paint
                    )
                }

                drawSideLabel("a=${a1.roundToInt()}", pB1, pC1, 15f, paint1)
                drawSideLabel("b=${b1.roundToInt()}", pA1, pC1, 15f, paint1)
                drawSideLabel("c=${c1.roundToInt()}", pA1, pB1, -15f, paint1)

                fun angleFromSides(opposite: Float, side1: Float, side2: Float): Float {
                    val cosVal = ((side1 * side1 + side2 * side2 - opposite * opposite) /
                            (2f * side1 * side2)).coerceIn(-1f, 1f)
                    return Math.toDegrees(acos(cosVal.toDouble())).toFloat()
                }

                val angleA1 = angleFromSides(a1, b1, c1)
                val angleB1 = angleFromSides(b1, a1, c1)
                val angleC1 = 180f - angleA1 - angleB1


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
                    val verticalRadius = radius * abs(tilt1)

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
                val arcRadius = 40f * scale1
                drawAngleArc(pA1, pB1, pC1, angleA1, arcRadius)
                drawAngleArc(pB1, pA1, pC1, angleB1, arcRadius)
                drawAngleArc(pC1, pA1, pB1, angleC1, arcRadius)

                // --- Draw the angle labels INSIDE the triangle, following its rotation ---
                val newCentroid = (pA1 + pB1 + pC1) / 3f

                fun Offset.moveToward(target: Offset, fraction: Float): Offset {
                    return this + (target - this) * fraction
                }

                val labelFraction = 0.25f // how deep inside the triangle the label goes

                val labelPosA = pA1.moveToward(newCentroid, labelFraction)
                val labelPosB = pB1.moveToward(newCentroid, labelFraction)
                val labelPosC = pC1.moveToward(newCentroid, labelFraction)


                drawContext.canvas.nativeCanvas.drawText(
                    "${angleA1.roundToInt()}°",
                    labelPosA.x,
                    labelPosA.y,
                    paint1
                )
                drawContext.canvas.nativeCanvas.drawText(
                    "${angleB1.roundToInt()}°",
                    labelPosB.x,
                    labelPosB.y,
                    paint1
                )
                drawContext.canvas.nativeCanvas.drawText(
                    "${angleC1.roundToInt()}°",
                    labelPosC.x,
                    labelPosC.y,
                    paint1
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

data class TrianglePoints(val A: Offset, val B: Offset, val C: Offset)

fun calculateTrianglePoints(a1: Float, b1: Float, c1: Float): TrianglePoints {
    val pointA = Offset(0f, 0f)
    val pointB = Offset(c1, 0f)
    val xC = (b1 * b1 - a1 * a1 + c1 * c1) / (2f * c1)
    val yCSq = (b1 * b1 - xC * xC).coerceAtLeast(0f)
    val yC = -sqrt(yCSq)
    val pointC = Offset(xC, yC)

    return TrianglePoints(pointA, pointB, pointC)
}

fun DrawScope.drawTriangle(A: Offset, B: Offset, C: Offset, color1: Color, color2: Color, colorLine: Color,
                           colorLineSelected: Color, isTriangleSelected: Boolean = false){
    // --- Drawing Code (remains mostly the same) ---
    val path = Path().apply {
        moveTo(A.x, A.y)
        lineTo(B.x, B.y)
        lineTo(C.x, C.y)
        close()
    }
    val cross = (B.x - A.x) * (C.y - A.y) - (B.y - A.y) * (C.x - A.x)
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
