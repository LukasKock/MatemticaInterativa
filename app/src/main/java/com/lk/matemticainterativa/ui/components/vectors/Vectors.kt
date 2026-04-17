package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.io.path.Path

@Composable
fun Vectors(){

    @Composable
    fun VectorsContent(){
        Box(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit){
                detectTransformGestures { centroid, pan, zoom, rotation ->{

                } }
            }){
        }
        Column(modifier = Modifier
            .fillMaxSize()) {
            Canvas(modifier = Modifier
                .fillMaxSize()){
                val canvasCenter = size.center
                val myVectorPoints = VectorPoints(canvasCenter, Offset(canvasCenter.x+50f, canvasCenter.y-50f))
                val path = Path().apply {
                    moveTo(myVectorPoints.startPointOffset.x, myVectorPoints.startPointOffset.y)
                    lineTo(myVectorPoints.endPointOffset.x, myVectorPoints.endPointOffset.y)
                    close()
                }
                drawPath(path, color = androidx.compose.ui.graphics.Color.Red)
            }
        }
    }
}

@Composable
private fun PortraitLayout(){

}

@Composable
fun LandscapeLayout(){

}
