package com.lk.matemticainterativa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lk.matemticainterativa.ui.login.LoginScreen
import com.lk.matemticainterativa.ui.main.MainScreen

@Composable
fun AppNav(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main/Lukas"){
        composable("login") { LoginScreen(navController)}
        composable("main/{username}"){ backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuário não encontrado"
            MainScreen(username)
        }}
}