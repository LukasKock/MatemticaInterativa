package com.lk.matemticainterativa.ui.components.SeparetedTest

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun CombinedCanvas() {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var scale by remember { mutableFloatStateOf(180f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // Global gesture handler
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    scale = scale.coerceIn(20f, 1000f)
                    offset += pan
                }
            }
    ) {
        // Pass shared state to both
        CartesianCanvas(scale = scale, offset = offset)
        DistanceBetweenPoints(offset = offset, scale = scale)
    }
}
