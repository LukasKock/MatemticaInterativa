package com.lk.matemticainterativa.ui.main


import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import com.lk.matemticainterativa.ui.components.similarTriangles.SimilarTriangles


@Composable
fun MainScreen(username: String) {
    SimilarTriangles(
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

//    RotatableTriangles(
//        a1 = 500f, b1 = 700f, c1 = 900f,
//        a2 = 400f, b2 = 560f, c2 = 600f,
//        initialOffset1 = Offset(100f, 0f),
//        initialOffset2 = Offset(-80f, -500f),
//        initialRotation1 = 100f,
//        initialRotation2 = 98f,
//        initialScale1 = 1f,
//        initialScale2 = 1.3f,
//        initialTilt2 = -1f,
//        areTrianglesSimilar = false,
//        explanationCorrect = "Parabéns, você acertou. Os triângulos não são semelhantes porque seus ângulos são diferentes",
//        explanationFalse = "Os triângulos não são semelhantes porque seus ângulos são diferentes")
}