package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

fun DrawScope.drawThreeVectors(vector1: VectorPoints, vector2: VectorPoints, resultVector: VectorPoints, operation: Operation,
                               color1: Color, color2: Color, colorResultVector: Color,
                               textColor: Color,
                               name1: String, name2: String, selectedVector: Int?){
    when (selectedVector) {
        1 -> {
            drawVector(vector1, color1, textColor, selectedVector, name1)
            drawVector(vector2, color2, textColor, null, name2)
            drawVector(
                resultVector, colorResultVector, textColor,null,
                if (operation == Operation.ADDITION) {
                    onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
                } else {
                    onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
                }
            )
        }
        2 -> {
            drawVector(vector1, color1, textColor, null, name1)
            drawVector(vector2, color2, textColor,selectedVector, name2)
            drawVector(
                resultVector, colorResultVector, textColor, null,
                if (operation == Operation.ADDITION) {
                    onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
                } else {
                    onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
                }
            )

        }
        3 -> {
            drawVector(vector1, color1, textColor, null, name1)
            drawVector(vector2, color2, textColor, null, name2)
            drawVector(
                resultVector, colorResultVector, textColor, selectedVector,
                if (operation == Operation.ADDITION) {
                    onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
                } else {
                    onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
                }
            )
        }
        else -> {
            drawVector(vector1, color1, textColor, null, name1)
            drawVector(vector2, color2, textColor, null, name2)
            drawVector(
                resultVector, colorResultVector, textColor, null,
                if (operation == Operation.ADDITION) {
                    onlyNameNoSign(name1) + " + " + onlyNameNoSign(name2)
                } else {
                    onlyNameNoSign(name1) + " - " + onlyNameNoSign(name2)
                }
            )
        }
    }
}
