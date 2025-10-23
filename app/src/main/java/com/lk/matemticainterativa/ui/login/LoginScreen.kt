package com.lk.matemticainterativa.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var message by remember { mutableStateOf("")}

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(Modifier.statusBarsPadding().padding(
        top = 16.dp,
        bottom = 16.dp,
        start = if(isLandscape) 32.dp else 16.dp,
        end = 16.dp)
        .verticalScroll(scrollState)
        .fillMaxSize())
    {
        TextField(value = username, onValueChange = {username = it}, label = { Text("Usuário") })
        Spacer(Modifier.height(6.dp))
        TextField(value = password, onValueChange = {password = it}, label = { Text("Senha") })
        Spacer(Modifier.height(6.dp))

        Button(onClick = {
            viewModel.login(username, password){ success ->
                if(success){
                    navController.navigate("main/$username"){
                        popUpTo("login") {inclusive = true}
                    }
                } else {
                    message = "Usuário ou senha incorretos"
                }
            }
        }) {
            Text("Login")
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            viewModel.register(username, password)
            message = "Usuário registrado com sucesso"
        }) {
            Text("Cadastrar")
        }
        Spacer(Modifier.height(8.dp))
        Text(message)
    }
}