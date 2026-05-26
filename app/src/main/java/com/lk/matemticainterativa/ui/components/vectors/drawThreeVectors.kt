package com.lk.matemticainterativa.ui.components.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope



fun DrawScope.drawThreeVectors(vector1: VectorPoints, vector2: VectorPoints, resultVector: VectorPoints, k1: Float, k2: Float,
                               k1Display: Float, k2Display: Float,
                               color1: Color, color2: Color, colorResultVector: Color,
                               textColor: Color,
                               name1: String, name2: String, selectedVector: Int?){


    val k1DisplayNo1f = when {
        k1Display == 1f || k1Display == -1f-> {
            ""
        }
        else -> {
            k1Display
        }
    }

    val k2DisplayNo1f = when {
        k2Display == 1f || k2Display == -1f -> {
            ""
        }
        else -> {
            k2Display
        }
    }
    val nameToDisplay = if (k1Display > 0f && k2Display > 0f) {
        "${k1DisplayNo1f} " + onlyNameNoSign(name1) + " + " + "${k2DisplayNo1f} " + onlyNameNoSign(name2)
    } else if (k1Display > 0f && k2Display < 0f){
        "${k1DisplayNo1f} " + onlyNameNoSign(name1) + " - " + "${k2DisplayNo1f} " + onlyNameNoSign(name2)
    } else if (k1Display < 0f && k2Display > 0f){
        "- ${k1DisplayNo1f} " + onlyNameNoSign(name1) + " + " + "${k2DisplayNo1f} " + onlyNameNoSign(name2)
    } else {
        "- ${k1DisplayNo1f} " + onlyNameNoSign(name1) + " - " + "${k2DisplayNo1f} " +  onlyNameNoSign(name2)
    }

    when (selectedVector) {
        1 -> {
            drawVector(vector1, color1, textColor, selectedVector, name1, k1)
            drawVector(vector2, color2, textColor, null, name2, k2)
            drawVector(
                resultVector, colorResultVector, textColor,null, nameToDisplay, 1f)
        }
        2 -> {
            drawVector(vector1, color1, textColor, null, name1, k1)
            drawVector(vector2, color2, textColor,selectedVector, name2, k2)
            drawVector(
                resultVector, colorResultVector, textColor, null, nameToDisplay, 1f)

        }
        3 -> {
            drawVector(vector1, color1, textColor, null, name1, k1)
            drawVector(vector2, color2, textColor, null, name2, k2)
            drawVector(
                resultVector, colorResultVector, textColor, selectedVector, nameToDisplay, 1f)
        }
        else -> {
            drawVector(vector1, color1, textColor, null, name1, k1)
            drawVector(vector2, color2, textColor, null, name2, k2)
            drawVector(
                resultVector, colorResultVector, textColor, null, nameToDisplay, 1f)
        }
    }
}
