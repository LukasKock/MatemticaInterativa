@file:OptIn(ExperimentalComposeUiApi::class)

package com.lk.matemticainterativa.ui.components.similarTriangles

import android.content.res.Configuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lk.matemticainterativa.ui.components.questionfeedback.BalloonAnimation

@Composable
fun SimilarTriangles(
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
    areTrianglesSimilar: Boolean,
    explanationCorrect: String,
    explanationFalse: String
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

    var wereYesOrNoButtonsPressed by rememberSaveable{ mutableStateOf(false) }
    var isInMovingMode by rememberSaveable { mutableStateOf( false ) }
    var showSuccess by rememberSaveable { mutableStateOf(false) }
    var showCongratsMessage by rememberSaveable { mutableStateOf(false)}



    val titleText = when {
        showSuccess || showCongratsMessage ->
            "Os triângulos são semelhantes!"
        isInMovingMode ->
            "Toque num dos triângulos para selecioná-lo. " +
                    "Mova e aumente ou diminua eles até que fiquem do mesmo tamanho"
        !isInMovingMode && wereYesOrNoButtonsPressed -> "Atividade terminada"
        else ->
            "Os triângulos a seguir são semelhantes?"
    }

    @Composable
    fun TriangleCanvasContent(
        /* pass needed states if necessary */
    ) {
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


                pA1 = transform(canvasCenter,pointA1, centroidModel1, scale1, tilt1, rotationRad1, panOffset1)
                pB1 = transform(canvasCenter,pointB1, centroidModel1, scale1, tilt1, rotationRad1, panOffset1)
                pC1 = transform(canvasCenter,pointC1, centroidModel1, scale1, tilt1, rotationRad1, panOffset1)

                pA2 = transform(canvasCenter,pointA2, centroidModel2, scale2, tilt2, rotationRad2, panOffset2)
                pB2 = transform(canvasCenter,pointB2, centroidModel2, scale2, tilt2, rotationRad2, panOffset2)
                pC2 = transform(canvasCenter,pointC2, centroidModel2, scale2, tilt2, rotationRad2, panOffset2)



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

            AnimatedFloatButton(
                isTriangle1Selected,
                isTriangle2Selected,
                initialTilt1,
                initialTilt2,
                onTiltChange = {
                        newTilt ->
                    when {
                        isTriangle1Selected -> tilt1 = newTilt
                        isTriangle2Selected -> tilt2 = newTilt
                    }
                },
                visible = wereYesOrNoButtonsPressed
            )

            if(areTrianglesSimilar) isInMovingMode = wereYesOrNoButtonsPressed


            LaunchedEffect(isInMovingMode, pA1, pB1, pC1, pA2, pB2, pC2) {
                if (isInMovingMode) {
                    val triangle1 = TrianglePoints(pA1, pB1, pC1)
                    val triangle2 = TrianglePoints(pA2, pB2, pC2)

                    if (areTrianglesAligned(triangle1, triangle2)) {
                        isInMovingMode = false
                        showSuccess = true
                    }
                }
            }
            BalloonAnimation(visible = showSuccess,
                onFinished = { showSuccess = false
                    showCongratsMessage = true
                    isTriangle1Selected = false
                    isTriangle2Selected = false})
        }
    }
    if(!isLandscape){
        PortraitLayout(
            backgroundColor = backgroundColor,
            text = titleText,
            textColor = textColor,
            content = { TriangleCanvasContent() },
            buttonsContent = {YesOrNoButtons(areTrianglesSimilar = areTrianglesSimilar,
                explanationCorrect = explanationCorrect,
                explanationFalse = explanationFalse,
                visible = !wereYesOrNoButtonsPressed,
                onFinished = {wereYesOrNoButtonsPressed = true})}
        )
    } else {
        LandscapeLayout(
            backgroundColor = backgroundColor,
            text = titleText,
            textColor = textColor,
            content = { TriangleCanvasContent() },
            buttonsContent = {YesOrNoButtons(areTrianglesSimilar = areTrianglesSimilar,
                explanationCorrect = explanationCorrect,
                explanationFalse = explanationFalse,
                visible = !wereYesOrNoButtonsPressed,
                onFinished = {wereYesOrNoButtonsPressed = true})}
        )
    }
}
@Composable
private fun PortraitLayout(
    backgroundColor: Color,
    text: String,
    textColor: Color,
    content: @Composable () -> Unit,
    buttonsContent: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = textColor
        )

        Box(
            modifier = Modifier
                .weight(1f)   // IMPORTANT
                .fillMaxWidth()
        ) {
            content()
            buttonsContent()
        }
    }
}

@Composable
private fun LandscapeLayout(
    backgroundColor: Color,
    text: String,
    textColor: Color,
    content: @Composable () -> Unit,
    buttonsContent: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {

        // LEFT PANEL
        Box(
            modifier = Modifier
                .weight(0.35f)  // adjust this ratio
                .fillMaxHeight()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = text,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = textColor
                )
                buttonsContent()
            }
        }

        // RIGHT PANEL (Canvas)
        Box(
            modifier = Modifier
                .weight(0.65f)
                .fillMaxHeight()
        ) {
            content()
        }
    }
}

