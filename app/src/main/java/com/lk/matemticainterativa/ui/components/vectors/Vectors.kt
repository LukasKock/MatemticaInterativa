package com.lk.matemticainterativa.ui.components.vectors

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration

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

                        when(selectedVector){
                            1 -> vector1 = vector1.copy(startPoint = vector1.startPoint + dragAmount,endPoint = vector1.endPoint + dragAmount)
                            2 -> vector2 = vector2.copy(startPoint = vector2.startPoint + dragAmount, endPoint = vector2.endPoint + dragAmount)
                        }
                    },
                    onDragEnd = {
                        selectedVector = null
                    })
            }){
            Canvas(modifier = Modifier
                .fillMaxSize()){

                drawVector(vector1, color1)
                drawVector(vector2, color2)

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
