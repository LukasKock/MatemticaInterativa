package com.lk.matemticainterativa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lk.matemticainterativa.ui.components.cartesianPlane.CartesianPlane
import com.lk.matemticainterativa.ui.components.similarTriangles.SimilarTriangles
import com.lk.matemticainterativa.ui.components.vectors.VectorPoints
import com.lk.matemticainterativa.ui.components.vectors.Vectors
import com.lk.matemticainterativa.ui.login.LoginScreen
import com.lk.matemticainterativa.ui.main.MainScreen
import com.lk.matemticainterativa.ui.menu.submenus.TrianglesSubMenuScreen
import com.lk.matemticainterativa.ui.menu.submenus.VectorsSubMenuScreen
import androidx.compose.ui.text.withStyle

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main/"){
        var triangleActivityNumber = 1
        var vectorActivityNumber = 1
        composable("login") { LoginScreen(navController)}
        composable("main/{username}"){ backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuário não encontrado"
            MainScreen(username = username, navController = navController)
        }

        //Triangle Activities:
        composable("triangles/"){
            TrianglesSubMenuScreen(navController)
        }
        composable("triangles/${triangleActivityNumber}"){
            SimilarTriangles(
                a1 = 1040f, b1 = 1040f, c1 = 650f,
                a2 = 800f, b2 = 800f, c2 = 500f,
                initialOffset1 = Offset(-150f, -200f),
                initialOffset2 = Offset(150f, 200f),
                initialRotation1 = 0f,
                initialRotation2 = 0f,
                initialScale1 = 1f,
                initialScale2 = 1f,
                initialTilt1 = 1f,
                initialTilt2 = 1f,
//                showSideA1 = false,
//                showSideB1 = false,
//                showSideC1 = false,
//                showAngleA1 = false,
//                showAngleB1 = false,
//                showAngleC1 = false,
//                showSideA2 = false,
//                showSideB2 = false,
//                showSideC2 = false,
//                showAngleA2 = false,
//                showAngleB2 = false,
//                showAngleC2 = false,
                questionText = buildAnnotatedString {
                    append("Os triângulos a seguir são semelhantes? ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Mova, aumente e/ou diminua-os até que fiquem sobrepostos.")
                    }
                },
                areTrianglesSimilar = true,
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes porque ele possui os três ângulos congruentes. "
                + "Note que seus ângulos e lados são congruentes quando os triângulos estão sobrepostos.",
                explanationFalse = "Os triângulos são semelhantes porque seus ângulos são congruentes e seus lados são homólogos.",
                navController = navController)
        }
        triangleActivityNumber++
        composable("triangles/${triangleActivityNumber}"){
            SimilarTriangles(
                a1 = 600f, b1 = 800f, c1 = 1000f,
                a2 = 300f, b2 = 400f, c2 = 500f,
                initialOffset1 = Offset(0f, 0f),
                initialOffset2 = Offset(33f, -112f),
                initialRotation1 = 0f,
                initialRotation2 = 0f,
                initialScale1 = 1f,
                initialScale2 = 1.3f,
                initialTilt1 = 1f,
                initialTilt2 = 1f,
                showSideA1 = false,
                showSideB1 = false,
//                showSideC1 = false,
//                showAngleA1 = false,
//                showAngleB1 = false,
                showAngleC1 = false,
                showSideA2 = false,
                showSideB2 = false,
//                showSideC2 = false,
//                showAngleA2 = false,
//                showAngleB2 = false,
                showAngleC2 = false,
                questionText = buildAnnotatedString {
                    append("Os triângulos a seguir são semelhantes? ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Os lados de 4 e 6 cm são paralelos.")
                    }
                },
                areTrianglesSimilar = true,
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes pelo caso A.L.A..",
                explanationFalse = "Os triângulos são semelhantes pelo caso A.L.A.. Mova-os e verifique que um se \"encaixa\" no outro.",
                navController = navController)
        }
        triangleActivityNumber++
        composable("triangles/${triangleActivityNumber}"){
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
                showSideA1 = false,
                showSideB1 = false,
//                showSideC1 = false,
                showAngleA1 = false,
                showAngleB1 = false,
//                showAngleC1 = false,
                showSideA2 = false,
                showSideB2 = false,
//                showSideC2 = false,
                showAngleA2 = false,
                showAngleB2 = false,
//                showAngleC2 = false,
                areTrianglesSimilar = true,
                questionText = buildAnnotatedString {
                    append("Os triângulos a seguir são semelhantes? ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Dê um \"flip\" se for necessário.")
                    }
                },
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes porque ele possui dois ângulos congruentes "
                + "(verifique isso na posição inicial).",
                explanationFalse = "Os triângulos são semelhantes porque seus ângulos são iguais.",
                navController = navController)
        }
        triangleActivityNumber++
        composable("triangles/${triangleActivityNumber}"){
                SimilarTriangles(
                a1 = 500f, b1 = 700f, c1 = 900f,
                a2 = 400f, b2 = 560f, c2 = 600f,
                initialOffset1 = Offset(100f, 0f),
                initialOffset2 = Offset(-80f, -500f),
                initialRotation1 = 100f,
                initialRotation2 = 98f,
                initialScale1 = 1f,
                initialScale2 = 1.3f,
                initialTilt2 = -1f,
//                showSideA1 = false,
//                showSideB1 = false,
//                showSideC1 = false,
//                showAngleA1 = false,
//                showAngleB1 = false,
//                showAngleC1 = false,
//                showSideA2 = false,
//                showSideB2 = false,
//                showSideC2 = false,
//                showAngleA2 = false,
//                showAngleB2 = false,
//                showAngleC2 = false,
                    questionText = buildAnnotatedString {
                        append("Os triângulos a seguir são semelhantes? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Tente sobrepor um no outro.")
                        }
                    },
                areTrianglesSimilar = false,
                explanationCorrect = "Parabéns, você acertou. Os triângulos não são semelhantes porque seus três ângulos são diferentes. "
                    + "Veja que não é possível sobrepor um no outro.",
                explanationFalse = "Os triângulos não são semelhantes porque seus três ângulos são diferentes. " +
                        "Veja que não é possível sobrepor um no outro.",
                    navController = navController)
        }
        triangleActivityNumber++
        composable("triangles/${triangleActivityNumber}"){
            SimilarTriangles(
                a1 = 1040f, b1 = 1040f, c1 = 650f,
                a2 = 520f, b2 = 520f, c2 = 325f,
                initialOffset1 = Offset(-150f, -200f),
                initialOffset2 = Offset(150f, 200f),
                initialRotation1 = 0f,
                initialRotation2 = 0f,
                initialScale1 = 1f,
                initialScale2 = 1f,
                initialTilt1 = -1f,
                initialTilt2 = 1f,
//                showSideA1 = false,
//                showSideB1 = false,
//                showSideC1 = false,
                showAngleA1 = false,
                showAngleB1 = false,
                showAngleC1 = false,
//                showSideA2 = false,
//                showSideB2 = false,
//                showSideC2 = false,
                showAngleA2 = false,
                showAngleB2 = false,
                showAngleC2 = false,
                questionText = buildAnnotatedString {
                    append("Os triângulos a seguir são semelhantes?")
                },
                areTrianglesSimilar = true,
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes pelo caso L.L.L. (lado-lado-lado). "
                + "A razão de proporção é dois",
                explanationFalse = "Os triângulos são semelhantes pelo caso L.L.L. (lado-lado-lado). "
                        + "A razão de proporção é dois",
                navController = navController)
        }
        triangleActivityNumber++
        composable("triangles/${triangleActivityNumber}"){
            SimilarTriangles(
                a1 = 1200f, b1 = 750f, c1 = 850f,
                a2 = 550f, b2 = 540f, c2 = 850f,
                initialOffset1 = Offset(-150f, -200f),
                initialOffset2 = Offset(18f, -67f),
                initialRotation1 = 0f,
                initialRotation2 = 0f,
                initialScale1 = 1f,
                initialScale2 = 1f,
                initialTilt1 = 1f,
                initialTilt2 = 1f,
                showSideA1 = false,
                showSideB1 = false,
                showSideC1 = false,
                showAngleA1 = false,
                showAngleB1 = false,
//                showAngleC1 = false,
                showSideA2 = false,
                showSideB2 = false,
                showSideC2 = false,
//                showAngleA2 = false,
                showAngleB2 = false,
                showAngleC2 = false,
                questionText = buildAnnotatedString {
                    append("Os triângulos a seguir são semelhantes? ")
                },
                areTrianglesSimilar = false,
                explanationCorrect = "Parabéns, você acertou. Os triângulos não são semelhantes porque possui um ângulo igual mas o outro diferente, "
                + "então o terceiro só pode ser diferente também.",
                explanationFalse = "Os triângulos não são semelhantes porque possui um ângulo igual mas o outro diferente, "
                        + "então o terceiro só pode ser diferente também.",
                navController = navController)
        }
        triangleActivityNumber++
        composable("triangles/${triangleActivityNumber}"){
            SimilarTriangles(
                a1 = 480f, b1 = 480f, c1 = 480f,
                a2 = 400f, b2 = 500f, c2 = 400f,
                initialOffset1 = Offset(-200f, -150f),
                initialOffset2 = Offset(270f, 0f),
                initialRotation1 = -20f,
                initialRotation2 = -20f,
                initialScale1 = 1f,
                initialScale2 = 1f,
                initialTilt1 = 1f,
                initialTilt2 = -1f,
//                showSideA1 = false,
//                showSideB1 = false,
//                showSideC1 = false,
//                showAngleA1 = false,
//                showAngleB1 = false,
//                showAngleC1 = false,
//                showSideA2 = false,
//                showSideB2 = false,
//                showSideC2 = false,
//                showAngleA2 = false,
//                showAngleB2 = false,
//                showAngleC2 = false,
                questionText = buildAnnotatedString {
                    append("Os triângulos a seguir são semelhantes? ")
                },
                areTrianglesSimilar = false,
                explanationCorrect = "Parabéns, você acertou. Os triângulos não são semelhantes porque possui dois ângulos diferentes, "
                        + "então o terceiro só pode ser diferente também.",
                explanationFalse = "Os triângulos não são semelhantes porque possui dois ângulos diferentes, "
                        + "então o terceiro só pode ser diferente também.",
                navController = navController)
        }

        //Vectors Activities:
        composable("vectors/") { VectorsSubMenuScreen(navController) }
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(350f, -350f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(350f, 350f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-300f, -200f),
            k1 = 1f,
            k2 = 1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(0f, -100f), Offset(350f, -450f)),
            vector2 = VectorPoints(Offset(50f, 100f), Offset(400f, 450f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-300f, -200f),
            k1 = 1f,
            k2 = 1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(0f, -100f), Offset(300f, -300f)),
            vector2 = VectorPoints(Offset(50f, 150f), Offset(450f, 450f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "u",
            name2 = "v",
            centerOffset = Offset(-300f, 0f),
            k1 = 1f,
            k2 = 1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(-100f, 0f), Offset(-400f, 150f)),
            vector2 = VectorPoints(Offset(0f, -200f), Offset(-300f, -600f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Cyan,
            name1 = "u",
            name2 = "v",
            centerOffset = Offset(100f, -200f),
            k1 = 1f,
            k2 = -1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(80f, -400f), Offset(430f, -50f)),
            vector2 = VectorPoints(Offset(80f, 200f), Offset(430f, -150f)),
            color1 = Color.Red,
            color2 = Color.Green,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-300f, -80f),
            k1 = 1f,
            k2 = 1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(80f, -400f), Offset(430f, -50f)),
            vector2 = VectorPoints(Offset(80f, 200f), Offset(430f, -150f)),
            color1 = Color.Red,
            color2 = Color.Green,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-300f, -80f),
            k1 = -1f,
            k2 = 1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(-120f, -500f), Offset(230f, -150f)),
            vector2 = VectorPoints(Offset(-120f, 100f), Offset(230f, -250f)),
            color1 = Color.Red,
            color2 = Color.Green,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(200f, -80f),
            k1 = -1f,
            k2 = -1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(350f, -350f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(350f, 350f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-400f, -200f),
            k1 = 1.5f,
            k2 = 1f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(0f, -100f), Offset(350f, -450f)),
            vector2 = VectorPoints(Offset(50f, 100f), Offset(400f, 450f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-450f, -200f),
            k1 = 1.25f,
            k2 = 1.5f,
            navController = navController
        ) }
        vectorActivityNumber++
        composable("vectors/${vectorActivityNumber}"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(250f, -250f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(250f, 250f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "v",
            name2 = "w",
            centerOffset = Offset(-200f, 0f),
            k1 = 2f,
            k2 = -2f,
            navController = navController
        ) }

        //Cartesian Plane Activity (mudar)
        composable("cartesian/") { CartesianPlane(moveEnabled = false) }
    }
}