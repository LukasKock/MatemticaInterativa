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
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun Vectors(vector1: VectorPoints, vector2: VectorPoints, color1: Color, color2: Color){

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    var vector1 by remember { mutableStateOf(vector1) }
    var vector2 by remember { mutableStateOf(vector2) }

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
                    }},
                    onDrag = { change, dragAmount ->
                        change.consume()
                        val threshold = 17f
                        when(selectedVector){
                            1 -> {
                                vector1 = vector1.copy(startPoint = vector1.startPoint + dragAmount,endPoint = vector1.endPoint + dragAmount)
                                val delta = calculateDelta(vector1, vector2)
                                if(abs(delta.x) < threshold && abs(delta.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta.x) && abs(dragAmount.y) < abs(delta.y)){
                                    vector1 = vector2.copy(startPoint = vector1.startPoint + delta, endPoint = vector1.endPoint + delta)
                                }
                                val delta2 = calculateDelta(vector2, vector1)
                                if(abs(delta2.x) < threshold && abs(delta2.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta2.x) && abs(dragAmount.y) < abs(delta2.y)){
                                    vector1 = vector1.copy(startPoint = vector1.startPoint - delta2, endPoint = vector1.endPoint - delta2)
                                }
                            }
                            2 ->{
                                vector2 = vector2.copy(startPoint = vector2.startPoint + dragAmount, endPoint = vector2.endPoint + dragAmount)
                                val delta = calculateDelta(vector2, vector1)
                                if(abs(delta.x) < threshold && abs(delta.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta.x) && abs(dragAmount.y) < abs(delta.y)){
                                    vector2 = vector1.copy(startPoint = vector2.startPoint + delta, endPoint = vector2.endPoint + delta)
                                }
                                val delta2 = calculateDelta(vector1, vector2)
                                if(abs(delta2.x) < threshold && abs(delta2.y) < threshold &&
                                    abs(dragAmount.x) < abs(delta2.x) && abs(dragAmount.y) < abs(delta2.y)){
                                    vector2 = vector2.copy(startPoint = vector2.startPoint - delta2, endPoint = vector2.endPoint - delta2)
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
                    }
                    else if(selectedVector == 2){
                        vector2 = invertVectorKeepStart(vector2)
                    }
                }
            ) {
                Text("Invert vector")
            }
            Canvas(modifier = Modifier
                .fillMaxSize()){
                drawTwoVectors(vector1, vector2, color1, color2, selectedVector)
                if(calculateDeltasFloat(vector1,vector2)){
                    drawSnapVectorsLine(vector1, vector2)
                }
                if(calculateDeltasFloat(vector2,vector1)){
                    drawSnapVectorsLine(vector2, vector1)
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
fun DrawScope.drawTwoVectors(vector1: VectorPoints, vector2: VectorPoints, color1: Color, color2: Color, selectedVector: Int?){
    if(selectedVector == 1){
        drawVector(vector1, color1, selectedVector)
        drawVector(vector2, color2, null)
    }
    else if(selectedVector ==2){
        drawVector(vector1, color1, null)
        drawVector(vector2, color2, selectedVector)
    } else{
        drawVector(vector1, color1, selectedVector)
        drawVector(vector2, color2, selectedVector)
    }
}