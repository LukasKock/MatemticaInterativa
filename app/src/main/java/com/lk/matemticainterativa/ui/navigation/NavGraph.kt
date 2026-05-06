package com.lk.matemticainterativa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lk.matemticainterativa.ui.components.CombinedTest.CartesianWithDistance
import com.lk.matemticainterativa.ui.components.similarTriangles.SimilarTriangles
import com.lk.matemticainterativa.ui.components.vectors.Operation
import com.lk.matemticainterativa.ui.components.vectors.VectorPoints
import com.lk.matemticainterativa.ui.components.vectors.Vectors
import com.lk.matemticainterativa.ui.login.LoginScreen
import com.lk.matemticainterativa.ui.main.MainScreen
import com.lk.matemticainterativa.ui.menu.submenus.TrianglesSubMenuScreen
import com.lk.matemticainterativa.ui.menu.submenus.VectorsSubMenuScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main/"){
        composable("login") { LoginScreen(navController)}
        composable("main/{username}"){ backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuário não encontrado"
            MainScreen(username = username, navController = navController)
        }
        composable("triangles/"){
            TrianglesSubMenuScreen(navController)
        }
        composable("triangles/1"){
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
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes porque ele possui dois ângulos homólogos",
                explanationFalse = "Os triângulos são semelhantes porque seus ângulos são iguais",
                navController = navController)
        }
        composable("triangles/2"){
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
                areTrianglesSimilar = false,
                explanationCorrect = "Parabéns, você acertou. Os triângulos não são semelhantes porque seus ângulos são diferentes",
                explanationFalse = "Os triângulos não são semelhantes porque seus ângulos são diferentes",
                    navController = navController)
        }
        composable("triangles/3"){
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
                areTrianglesSimilar = true,
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes porque ele possui dois ângulos homólogos",
                explanationFalse = "Os triângulos são semelhantes porque seus ângulos são iguais",
                navController = navController)
        }
        composable("triangles/4"){
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
                areTrianglesSimilar = true,
                explanationCorrect = "Parabéns, você acertou. Os triângulos são semelhantes porque ele possui dois ângulos homólogos",
                explanationFalse = "Os triângulos são semelhantes porque seus ângulos são iguais",
                navController = navController)
        }
        composable("triangles/5"){
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
                areTrianglesSimilar = false,
                explanationCorrect = "Parabéns, você acertou. Os triângulos não são semelhantes porque seus ângulos são diferentes",
                explanationFalse = "Os triângulos não são semelhantes porque seus ângulos são diferentes",
                navController = navController)
        }
        composable("cartesian/"){ CartesianWithDistance() }
        composable("vectors/") { VectorsSubMenuScreen(navController) }
        composable("vectors/1"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(300f, -200f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(400f, 300f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "u",
            name2 = "v",
            centerOffset = Offset(-300f, 0f),
            operation = Operation.ADDITION,
            navController = navController
        ) }
        composable("vectors/2"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(-300f, 150f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(-300f, -400f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Cyan,
            name1 = "u",
            name2 = "v",
            centerOffset = Offset(100f, -60f),
            operation = Operation.SUBTRACTION,
            navController = navController
        ) }
        composable("vectors/3"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(350f, -350f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(350f, 350f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-250f, 0f),
            operation = Operation.ADDITION,
            navController = navController
        ) }
        composable("vectors/4"){ Vectors(
            vector1 = VectorPoints(Offset(0f, 0f), Offset(350f, 350f)),
            vector2 = VectorPoints(Offset(0f, 0f), Offset(350f, -350f)),
            color1 = Color.Red,
            color2 = Color.Blue,
            colorResultVector = Color.Magenta,
            name1 = "a",
            name2 = "b",
            centerOffset = Offset(-200f, -80f),
            operation = Operation.SUBTRACTION,
            navController = navController
        ) }
    }
}