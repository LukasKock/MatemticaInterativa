package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope



fun DrawScope.drawThreeVectors(vector1: VectorPoints, vector2: VectorPoints, resultVector: VectorPoints, k1: Float, k2: Float,
                               k1Initial: Float, k2Initial: Float,
                               color1: Color, color2: Color, colorResultVector: Color,
                               textColor: Color,
                               name1: String, name2: String, selectedVector: Int?,
                               isVector1Inverted: Boolean, isVector2Inverted: Boolean){


    val k1DisplayNo1f = when {
        k1Initial == 1f || k1Initial == -1f -> {
            ""
        }
        else -> {
            k1Initial
        }
    }

    val k2DisplayNo1f = when {
        k2Initial == 1f || k2Initial == -1f -> {
            ""
        }
        else -> {
            k2Initial
        }
    }
    val nameToDisplay = if (k1Initial > 0f && k2Initial > 0f) {
        "$k1DisplayNo1f " + name1 + " + " + "$k2DisplayNo1f " + name2
    } else if (k1Initial > 0f && k2Initial < 0f){
        "$k1DisplayNo1f " + name1 + " - " + "${k2DisplayNo1f.toString().substringAfter("-")} " + name2
    } else if (k1Initial < 0f && k2Initial > 0f){
        "- ${k1DisplayNo1f.toString().substringAfter("-")} " + name1 + " + " + "$k2DisplayNo1f " + name2
    } else {
        "- ${k1DisplayNo1f.toString().substringAfter("-")} " + name1 + " - " + "${k2DisplayNo1f.toString().substringAfter("-")} " +  name2
    }

    when (selectedVector) {
        1 -> {
            drawVector(vector1, color1, textColor, selectedVector, name1, k1, isVector1Inverted)
            drawVector(vector2, color2, textColor, null, name2, k2, isVector2Inverted)
            drawVector(
                resultVector, colorResultVector, textColor,null, nameToDisplay, 1f, false)
        }
        2 -> {
            drawVector(vector1, color1, textColor, null, name1, k1, isVector1Inverted)
            drawVector(vector2, color2, textColor,selectedVector, name2, k2, isVector2Inverted)
            drawVector(
                resultVector, colorResultVector, textColor, null, nameToDisplay, 1f, false)

        }
        3 -> {
            drawVector(vector1, color1, textColor, null, name1, k1, isVector1Inverted)
            drawVector(vector2, color2, textColor, null, name2, k2, isVector2Inverted)
            drawVector(
                resultVector, colorResultVector, textColor, selectedVector, nameToDisplay, 1f, false)
        }
        else -> {
            drawVector(vector1, color1, textColor, null, name1, k1, isVector1Inverted)
            drawVector(vector2, color2, textColor, null, name2, k2, isVector2Inverted)
            drawVector(
                resultVector, colorResultVector, textColor, null, nameToDisplay, 1f, false)
        }
    }
}
