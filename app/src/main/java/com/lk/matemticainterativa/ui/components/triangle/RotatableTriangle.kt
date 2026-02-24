@file:OptIn(ExperimentalComposeUiApi::class)

package com.lk.matemticainterativa.ui.components.triangle

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lk.matemticainterativa.ui.components.questionfeedback.QuestionFeedbackPopup
import kotlin.math.*

@Composable
fun RotatableTriangle(
    a1: Float, // side opposite vertex A (BC)
    b1: Float, // side opposite vertex B (AC)
    c1: Float,  // side opposite vertex C (AB)
    a2: Float, // side opposite vertex A (BC)
    b2: Float, // side opposite vertex B (AC)
    c2: Float,  // side opposite vertex C (AB)
    initialOffset1: Offset = Offset.Zero,
    initialOffset2: Offset = Offset.Zero,
    initialRotation1: Float = 0f,
    initialRotation2: Float = 0f,
    initialScale1: Float = 1f,
    initialScale2: Float = 1f,
    initialTilt1: Float = -1f,
    initialTilt2: Float = -1f,
    areTrianglesSimilar: Boolean
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val isDark = isSystemInDarkTheme()
    val backgroundColor = if(isDark)  Color(0xFF121212) else Color(0xFFFFFFFF)
    val triangleOutlineColor = if (isDark) Color(0xFFB0BEC5) else Color.Black
    val triangleOutlineColorSelected = if(isDark) Color(0xFFDC3A0A) else Color.Red
    val textColor = if (isDark) Color(0xFFE0E0E0) else Color.Black
    val triangle1Color = if(isDark) Color(0xFF0B3B4B) else Color.Cyan
    val triangle2Color = if(isDark) Color(0xFF483E07) else Color.Yellow
    val triangle3Color = if(isDark) Color(0xFF57064B) else Color(0xFFE17E5C)
    val triangle4Color = if(isDark) Color(0xFF620926) else Color(0xFFEA719A)

    val validTriangle = remember(a1, b1, c1, a2, b2, c2) {
        (a1 + b1 > c1) && (a1 + c1 > b1) && (b1 + c1 > a1)
                ||
                (a2 + b2 > c2) && (a2 + c2 > b2) && (b2 + c2 > a2)
    }

    var rotation1 by rememberSaveable { mutableFloatStateOf(initialRotation1) }
    var rotation2 by rememberSaveable { mutableFloatStateOf(initialRotation2) }
    var tilt1 by rememberSaveable { mutableFloatStateOf(initialTilt1) }
    var tilt2 by rememberSaveable { mutableFloatStateOf(initialTilt2) }
    var scale1 by rememberSaveable { mutableFloatStateOf(initialScale1) }
    var scale2 by rememberSaveable { mutableFloatStateOf(initialScale2) }

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
    var panOffset1 by rememberSaveable(stateSaver = offsetSaver1) { mutableStateOf(initialOffset1) }
    var panOffset2 by rememberSaveable(stateSaver = offsetSaver2) { mutableStateOf(initialOffset2) }

    var pA1 by remember { mutableStateOf(Offset.Zero) }
    var pB1 by remember { mutableStateOf(Offset.Zero) }
    var pC1 by remember { mutableStateOf(Offset.Zero) }

    var pA2 by remember { mutableStateOf(Offset.Zero) }
    var pB2 by remember { mutableStateOf(Offset.Zero) }
    var pC2 by remember { mutableStateOf(Offset.Zero) }

    var isYesOrNoButtonsPressed by rememberSaveable{ mutableStateOf(false) }
    var isInMovingMode by rememberSaveable { mutableStateOf( false ) }


    Column(modifier = Modifier.fillMaxSize()
        .background(color = backgroundColor)
    ) {
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = if(!isLandscape)
                Modifier.padding(8.dp)
            else
                Modifier.padding(start = 48.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            text = if(!isInMovingMode) "Os triângulos a seguir são semelhantes?" else "Mova os triângulos até que eles " +
                    "fiquem do mesmo tamanho",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = textColor
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, gestureZoom, gestureRotate ->
                        if(!isInMovingMode){
                            return@detectTransformGestures
                        }
                        if(isTriangle1Selected) {
                            rotation1 += gestureRotate
                            scale1 = (scale1 * gestureZoom).coerceIn(0.3f, 6f)
                            panOffset1 += pan // Accumulate the pan gesture
                        } else if(isTriangle2Selected){
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
                        if(!isInMovingMode){
                            return@detectTapGestures
                        }
                        val isTapOnTriangle1 = isPointInTriangle(tapOffset, pA1, pB1, pC1)
                        val isTapOnTriangle2 = isPointInTriangle(tapOffset, pA2, pB2, pC2)

                        if (isTapOnTriangle1 && isTapOnTriangle2) {
                            if(isTriangle1Selected){
                                isTriangle1Selected = false
                                isTriangle2Selected = true
                            } else{
                                isTriangle1Selected = true
                                isTriangle2Selected = false
                            }
                        } else if(isTapOnTriangle1) {
                            isTriangle1Selected = true
                            isTriangle2Selected = false
                        } else if(isTapOnTriangle2){
                            isTriangle2Selected = true
                            isTriangle1Selected = false
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



                fun drawTriangle1(){
                    drawTriangle(pA1,pB1,pC1,triangle3Color,triangle4Color,triangleOutlineColor,
                        triangleOutlineColorSelected, isTriangle1Selected)
                    drawLabelsAndAngles(textColor, scale1, tilt1,a1, b1, c1, pA1, pB1, pC1)
                }
                fun drawTriangle2() {
                    drawTriangle(pA2,pB2,pC2,triangle2Color,triangle1Color,triangleOutlineColor,
                        triangleOutlineColorSelected, isTriangle2Selected)
                    drawLabelsAndAngles(textColor, scale2, tilt2, a2, b2, c2, pA2, pB2, pC2)
                }

                if(isTriangle1Selected){
                    drawTriangle2()
                    drawTriangle1()
                } else {
                    drawTriangle1()
                    drawTriangle2()
                }

            }
            if(isYesOrNoButtonsPressed){
                AnimatedFloatButton(
                    isTriangle1Selected,
                    isTriangle2Selected,
                    initialTilt1,
                    initialTilt2,
                ){ newTilt ->
                    when {
                        isTriangle1Selected -> tilt1 = newTilt
                        isTriangle2Selected -> tilt2 = newTilt
                    }
                }
                //OtherButtons()
            } else{
                YesOrNoButtons(areTrianglesSimilar = areTrianglesSimilar)
            }
            val triangle1 = TrianglePoints(pA1, pB1, pC1)
            val triangle2 = TrianglePoints(pA2, pB2, pC2)
        }
    }
}


@Composable
fun AnimatedFloatButton(
    isTriangle1Selected: Boolean,
    isTriangle2Selected: Boolean,
    initialTilt1: Float,
    initialTilt2: Float,
    onTiltChange: (Float) -> Unit
) {
    val initToggle1 = initialTilt1 != -1f
    val initToggle2 = initialTilt2 != -1f

    var toggled1 by rememberSaveable { mutableStateOf(initToggle1) }
    var toggled2 by rememberSaveable { mutableStateOf(initToggle2) }

    // Animate between -1f and 1f when switch changes
    val animatedFloat1 by animateFloatAsState(
        targetValue = if (toggled1) 1f else -1f,
        animationSpec = tween(
            durationMillis = 500, // 500 milliseconds
            easing = LinearEasing
        ),
        label = "FloatAnimation"
    )
    val animatedFloat2 by animateFloatAsState(
        targetValue = if (toggled2) 1f else -1f,
        animationSpec = tween(
            durationMillis = 500, // 500 milliseconds
            easing = LinearEasing
        ),
        label = "FloatAnimation"
    )
    // Whenever the animation updates, assign it to your tilt
    LaunchedEffect(animatedFloat1) {
        onTiltChange(animatedFloat1)
    }
    LaunchedEffect(animatedFloat2) {
        onTiltChange(animatedFloat2)
    }

    // Theme colors
    val isDark = isSystemInDarkTheme()
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if(isTriangle1Selected){
                    toggled1 = !toggled1
                } else if(isTriangle2Selected){
                    toggled2 = !toggled2
                }
            },
            enabled = isTriangle1Selected || isTriangle2Selected,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDark) colorScheme.primary else colorScheme.secondary,
                contentColor = colorScheme.onPrimary,
                disabledContentColor = if(isDark) Color(0xFF131212) else Color(0xFF808080),
                disabledContainerColor = if(isDark) Color(0xFF7F7F83) else Color(0xFFE1DEDE)
            )
        ){
            Text(
                text = "Flip",
                fontSize = 14.sp
            )
        }
    }
}
@Composable
fun YesOrNoButtons(areTrianglesSimilar: Boolean) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var showFeedback by remember { mutableStateOf(false) }
    var userAnswer by remember { mutableStateOf(false) }

    val isDark = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val buttons: @Composable (Modifier) -> Unit = { buttonModifier ->
            Button(
                modifier = buttonModifier,
                onClick = {
                    showFeedback = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDark) colorScheme.primary else Color(0xFF2585D3),
                    contentColor = colorScheme.onPrimary
                )
            ) { Text("Sim") }

            Button(
                modifier = buttonModifier,
                onClick = {
                    showFeedback = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDark) colorScheme.primary else Color(0xFF009688),
                    contentColor = colorScheme.onPrimary
                )
            ) { Text("Não") }
        }

        if (isLandscape) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                buttons(Modifier)
            }
        } else {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                buttons(Modifier.weight(1f))
            }
        }
    }
    if (showFeedback) {
        QuestionFeedbackPopup(
            isCorrect = areTrianglesSimilar,
            onDismiss = { showFeedback = false },
            explanation = if (areTrianglesSimilar)
                "Sim! Os Triangulos são semelhantes (explicação...)."
            else
                "Os triângulos são SIM semelhantes."
        )
    }
}
fun areTrianglesAligned(trianglePoints1: TrianglePoints, trianglePoints2: TrianglePoints): Boolean {
    val tolerance = 60f
    return abs(trianglePoints1.pA.x - trianglePoints2.pA.x) +
            abs(trianglePoints1.pA.y - trianglePoints2.pA.y) +

            abs(trianglePoints1.pB.x - trianglePoints2.pB.x) +
            abs(trianglePoints1.pB.y - trianglePoints2.pB.y) +

            abs(trianglePoints1.pC.x - trianglePoints2.pC.x) +
            abs(trianglePoints1.pC.y - trianglePoints2.pC.y) < tolerance
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

data class TrianglePoints(val pA: Offset, val pB: Offset, val pC: Offset)

fun calculateTrianglePoints(a1: Float, b1: Float, c1: Float): TrianglePoints {
    val pointA = Offset(0f, 0f)
    val pointB = Offset(c1, 0f)
    val xC = (b1 * b1 - a1 * a1 + c1 * c1) / (2f * c1)
    val yCSq = (b1 * b1 - xC * xC).coerceAtLeast(0f)
    val yC = -sqrt(yCSq)
    val pointC = Offset(xC, yC)

    return TrianglePoints(pointA, pointB, pointC)
}

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
fun DrawScope.drawLabelsAndAngles(textColor: Color, scale: Float, tilt: Float, a: Float, b: Float, c: Float,
                                  pA: Offset, pB: Offset, pC: Offset){
    val baseTextSize = 32f
    val paint = Paint().apply {
        color = textColor.toArgb()
        textSize = baseTextSize
        textAlign = Paint.Align.CENTER
    }

    fun drawSideLabel(text: String, p1: Offset, p2: Offset, dyOffset: Float = 0f, paint: Paint) {
        val mid = (p1 + p2) / 2f
        drawContext.canvas.nativeCanvas.drawText(
            text,
            mid.x,
            mid.y + dyOffset * scale,
            paint
        )
    }
    drawSideLabel("a=${a.roundToInt()}", pB, pC, 15f, paint)
    drawSideLabel("b=${b.roundToInt()}", pA, pC, 15f, paint)
    drawSideLabel("c=${c.roundToInt()}", pA, pB, -15f, paint)

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
