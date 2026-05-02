package com.lk.matemticainterativa.ui.components.vectors

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun Vectors(vector1: VectorPoints, vector2: VectorPoints, color1: Color, color2: Color, colorResultVector: Color, name1: String,
            name2: String, centerOffset: Offset, operation: Operation){

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var vector1 by remember { mutableStateOf(vector1) }
    var vector2 by remember { mutableStateOf(vector2) }
    var initialized by remember { mutableStateOf(false) }

    var name1 by remember { mutableStateOf(name1) }
    var name2 by remember { mutableStateOf(name2) }

    var resultVector by remember { mutableStateOf(VectorPoints(startPoint = Offset.Zero, endPoint = Offset.Zero)) }

    var selectedVector by remember { mutableStateOf<Int?>(null) }

    @Composable
    fun VectorsContent(){
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){
                detectDragGestures(onDragStart = { touch ->
                    val threshold = 40f
                    when {
                        distanceToSegment(touch, vector1) < threshold -> {
                            selectedVector = 1
                        }
                        distanceToSegment(touch, vector2) < threshold -> {
                            selectedVector = 2
                        }
                        distanceToSegment(touch, resultVector) < threshold -> {
                            selectedVector = 3
                        }
                    }},
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val threshold = 17f
                        when(selectedVector){
                            1 -> {
                                vector1 = vector1.copy(startPoint = vector1.startPoint + dragAmount,endPoint = vector1.endPoint + dragAmount)
                                val delta1 = calculateDeltaStartEnd(vector1, vector2)
                                if(abs(delta1.x) < threshold && abs(delta1.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta1.x) && abs(dragAmount.y) < abs(delta1.y)){
                                    vector1 = vector2.copy(startPoint = vector1.startPoint + delta1, endPoint = vector1.endPoint + delta1)
                                }
                                val delta2 = calculateDeltaStartEnd(vector2, vector1)
                                if(abs(delta2.x) < threshold && abs(delta2.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta2.x) && abs(dragAmount.y) < abs(delta2.y)){
                                    vector1 = vector1.copy(startPoint = vector1.startPoint - delta2, endPoint = vector1.endPoint - delta2)
                                }
                                val delta3 = calculateDeltaStartStart(vector2, vector1)
                                if(abs(delta3.x) < threshold && abs(delta3.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta3.x) && abs(dragAmount.y) < abs(delta3.y)){
                                    vector1 = vector1.copy(startPoint = vector1.startPoint - delta3, endPoint = vector1.endPoint - delta3)
                                }
                                val delta4 = calculateDeltaStartStart(resultVector, vector1)
                                if(abs(delta4.x) < threshold && abs(delta4.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta4.x) && abs(dragAmount.y) < abs(delta4.y)){
                                    vector1 = vector1.copy(startPoint = vector1.startPoint - delta4, endPoint = vector1.endPoint - delta4)
                                }
                                val delta5 = calculateDeltaStartEnd(resultVector, vector1)
                                if(abs(delta5.x) < threshold && abs(delta5.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta5.x) && abs(dragAmount.y) < abs(delta5.y)){
                                    vector1 = vector1.copy(startPoint = vector1.startPoint - delta5, endPoint = vector1.endPoint - delta5)
                                }
                            }
                            2 ->{
                                vector2 = vector2.copy(startPoint = vector2.startPoint + dragAmount, endPoint = vector2.endPoint + dragAmount)
                                val delta1 = calculateDeltaStartEnd(vector2, vector1)
                                if(abs(delta1.x) < threshold && abs(delta1.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta1.x) && abs(dragAmount.y) < abs(delta1.y)){
                                    vector2 = vector1.copy(startPoint = vector2.startPoint + delta1, endPoint = vector2.endPoint + delta1)
                                }
                                val delta2 = calculateDeltaStartEnd(vector1, vector2)
                                if(abs(delta2.x) < threshold && abs(delta2.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta2.x) && abs(dragAmount.y) < abs(delta2.y)){
                                    vector2 = vector2.copy(startPoint = vector2.startPoint - delta2, endPoint = vector2.endPoint - delta2)
                                }
                                val delta3 = calculateDeltaStartStart(vector1, vector2)
                                if(abs(delta3.x) < threshold && abs(delta3.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta3.x) && abs(dragAmount.y) < abs(delta3.y)){
                                    vector2 = vector2.copy(startPoint = vector2.startPoint - delta3, endPoint = vector2.endPoint - delta3)
                                }
                                val delta4 = calculateDeltaStartStart(resultVector, vector2)
                                if(abs(delta4.x) < threshold && abs(delta4.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta4.x) && abs(dragAmount.y) < abs(delta4.y)){
                                    vector2 = vector2.copy(startPoint = vector2.startPoint - delta4, endPoint = vector2.endPoint - delta4)
                                }
                                val delta5 = calculateDeltaStartEnd(resultVector, vector2)
                                if(abs(delta5.x) < threshold && abs(delta5.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta5.x) && abs(dragAmount.y) < abs(delta5.y)){
                                    vector2 = vector2.copy(startPoint = vector2.startPoint - delta5, endPoint = vector2.endPoint - delta5)
                                }
                            }
                            3 -> {
                                resultVector = resultVector.copy(startPoint = resultVector.startPoint + dragAmount, endPoint = resultVector.endPoint + dragAmount)
                                val delta1 = calculateDeltaStartStart(vector2, resultVector)
                                if(abs(delta1.x) < threshold && abs(delta1.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta1.x) && abs(dragAmount.y) < abs(delta1.y)){
                                    resultVector = resultVector.copy(startPoint = resultVector.startPoint - delta1, endPoint = resultVector.endPoint - delta1)
                                }
                                val delta2 = calculateDeltaStartStart(vector1, resultVector)
                                if(abs(delta2.x) < threshold && abs(delta2.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta2.x) && abs(dragAmount.y) < abs(delta2.y)){
                                    resultVector = resultVector.copy(startPoint = resultVector.startPoint - delta2, endPoint = resultVector.endPoint - delta2)
                                }
                                val delta3 = calculateDeltaStartEnd(resultVector, vector1)
                                if(abs(delta3.x) < threshold && abs(delta3.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta3.x) && abs(dragAmount.y) < abs(delta3.y)){
                                    resultVector = resultVector.copy(startPoint = resultVector.startPoint + delta3, endPoint = resultVector.endPoint + delta3)
                                }
                                val delta4 = calculateDeltaStartEnd(resultVector, vector2)
                                if(abs(delta4.x) < threshold && abs(delta4.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta4.x) && abs(dragAmount.y) < abs(delta4.y)){
                                    resultVector = resultVector.copy(startPoint = resultVector.startPoint + delta4, endPoint = resultVector.endPoint + delta4)
                                }
                                val delta5 = calculateDeltaStartEnd(vector1, resultVector)
                                if(abs(delta5.x) < threshold && abs(delta5.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta5.x) && abs(dragAmount.y) < abs(delta5.y)){
                                    resultVector = resultVector.copy(startPoint = resultVector.startPoint - delta5, endPoint = resultVector.endPoint - delta5)
                                }
                                val delta6 = calculateDeltaStartEnd(vector2, resultVector)
                                if(abs(delta6.x) < threshold && abs(delta6.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta6.x) && abs(dragAmount.y) < abs(delta6.y)){
                                    resultVector = resultVector.copy(startPoint = resultVector.startPoint - delta6, endPoint = resultVector.endPoint - delta6)
                                }
                            }
                        }



                    },
                    onDragEnd = {
                        selectedVector = null
                    })
            }
            .pointerInput(Unit){
                detectTapGestures {
                    val threshold = 40f

                    when {
                        distanceToSegment(it, vector1) < threshold -> {
                            selectedVector = 1
                        }
                        distanceToSegment(it, vector2) < threshold -> {
                            selectedVector = 2
                        }
                        distanceToSegment(it, resultVector) < threshold -> {
                            selectedVector = 3
                        }
                        else -> {
                            selectedVector = null
                        }
                    }
                }
            }){
            Button(
                modifier = Modifier.padding(60.dp).align(Alignment.BottomCenter),
                onClick = {
                    if(selectedVector == 1){
                        vector1 = invertVectorKeepStart(vector1)
                        name1 = invertSignInName(name1)
                    }
                    else if(selectedVector == 2){
                        vector2 = invertVectorKeepStart(vector2)
                        name2 = invertSignInName(name2)
                    }
                }
            ) {
                Text("Invert vector")
            }
            Canvas(modifier = Modifier
                .fillMaxSize()){
                val center = this.center + centerOffset

                if (!initialized) {
                    //initializing sum vector
                    if(operation == Operation.ADDITION){
                        resultVector = resultVector.copy(startPoint = center + (vector1.startPoint + vector2.startPoint),
                            endPoint = center + (vector1.endPoint + vector2.endPoint))
                    } else if(operation == Operation.SUBTRACTION){
                        resultVector = resultVector.copy(startPoint = center + (vector1.startPoint - vector2.startPoint),
                            endPoint = center + (vector1.endPoint - vector2.endPoint))
                    }

                    vector1 = vector1.copy(
                        startPoint = center,
                        endPoint = center + (vector1.endPoint - vector1.startPoint)
                    )
                    vector2 = vector2.copy(
                        startPoint = center,
                        endPoint = center + (vector2.endPoint - vector2.startPoint)
                    )
                    initialized = true
                }
                drawThreeVectors(vector1, vector2, resultVector, operation, color1, color2, colorResultVector, name1, name2, selectedVector)
                if(calculateDeltaStartEndFloat(vector1,vector2)){
                    drawSnapVectorsLineStartEnd(vector1, vector2)
                }
                if(calculateDeltaStartEndFloat(vector2,vector1)){
                    drawSnapVectorsLineStartEnd(vector2, vector1)
                }
                if(calculateDeltaStartEndFloat(vector1,resultVector)){
                    drawSnapVectorsLineStartEnd(vector1, resultVector)
                }
                if(calculateDeltaStartEndFloat(vector2,resultVector)){
                    drawSnapVectorsLineStartEnd(vector2, resultVector)
                }
                if(calculateDeltaStartEndFloat(resultVector,vector1)){
                    drawSnapVectorsLineStartEnd(resultVector, vector1)
                }
                if(calculateDeltaStartEndFloat(resultVector,vector2)){
                    drawSnapVectorsLineStartEnd(resultVector, vector2)
                }
                if(calculateDeltaStartStartFloat(vector2,vector1)){
                    drawSnapVectorsLineStartStart(vector2, vector1)
                }
                if(calculateDeltaStartStartFloat(vector2,resultVector)){
                    drawSnapVectorsLineStartStart(vector2, resultVector)
                }
                if(calculateDeltaStartStartFloat(vector1,resultVector)){
                    drawSnapVectorsLineStartStart(vector1, resultVector)
                }
            }

        }
    }
    if(isPortrait){
        PortraitLayout(content = { VectorsContent() })
    }
    else{
        LandscapeLayout ( content = { VectorsContent() } )
    }

}

@Composable
private fun PortraitLayout(content: @Composable () -> Unit){
    Column(modifier = Modifier.fillMaxSize()) {
        //Text
        Box(modifier = Modifier.fillMaxSize()){
            content()
        }
    }
}

@Composable
fun LandscapeLayout(content: @Composable () -> Unit){
    Row(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()){
            content()
        }
    }
}
fun DrawScope.drawThreeVectors(vector1: VectorPoints, vector2: VectorPoints, resultVector: VectorPoints, operation: Operation,
                               color1: Color, color2: Color, colorResultVector: Color,
                               name1: String, name2: String, selectedVector: Int?){
    if(selectedVector == 1){
        drawVector(vector1, color1, selectedVector, name1)
        drawVector(vector2, color2, null, name2)
        drawVector( resultVector, colorResultVector, null,
            if(operation == Operation.ADDITION) {
                onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
            }else {
                onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
            }
        )
    }
    else if(selectedVector ==2){
        drawVector(vector1, color1, null, name1)
        drawVector(vector2, color2, selectedVector, name2)
        drawVector( resultVector, colorResultVector, null,
            if(operation == Operation.ADDITION) {
                onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
            }else {
                onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
            }
        )

    }
    else if(selectedVector ==3){
        drawVector(vector1, color1, null, name1)
        drawVector(vector2, color2, null, name2)
        drawVector( resultVector, colorResultVector, selectedVector,
            if(operation == Operation.ADDITION) {
                onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
            }else {
                onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
            }
        )
    }
    else{
        drawVector(vector1, color1, null, name1)
        drawVector(vector2, color2, null, name2)
        drawVector( resultVector, colorResultVector, null,
            if(operation == Operation.ADDITION) {
                onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
            }else {
                onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
            }
        )
    }
}
