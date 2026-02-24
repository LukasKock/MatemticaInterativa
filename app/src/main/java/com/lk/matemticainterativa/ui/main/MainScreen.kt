package com.lk.matemticainterativa.ui.main


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import com.lk.matemticainterativa.ui.components.triangle.RotatableTriangle


@Composable
fun MainScreen(username: String) {
    RotatableTriangle(
        a1 = 600f, b1 = 800f, c1 = 1000f,
        a2 = 300f, b2 = 400f, c2 = 500f,
        initialOffset1 = Offset(0f, -150f),
        initialOffset2 = Offset(140f, -495f),
        initialRotation1 = 100f,
        initialRotation2 = 137f,
        initialScale1 = 1.3f,
        initialScale2 = 1.3f,
        initialTilt2 = 1f,
        areTrianglesSimilar = true,
        explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes porque seus ângulos são iguais",
        explanationFalse = "Os triângulos são semelhantes porque seus ângulos são iguais")
}