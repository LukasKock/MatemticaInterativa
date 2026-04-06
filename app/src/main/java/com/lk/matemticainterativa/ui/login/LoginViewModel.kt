package com.lk.matemticainterativa.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.lk.matemticainterativa.data.local.AppDataBase
import com.lk.matemticainterativa.data.local.User
import com.lk.matemticainterativa.okhttpapi.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db = Room.databaseBuilder(
        application,
        AppDataBase::class.java,
        "app_database"
    ).build()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState



    // REGISTER — sends to remote API + saves locally
    fun register(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Preencha todos os campos")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("A senha deve conter no mínimo 6 caracteres")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val id = UUID.randomUUID()
            // 2. Save locally in Room (with hashed password)
            withContext(Dispatchers.IO) {
                db.userDao().insertUser(
                    User(
                        id = id,
                        username = username,
                        email = email,
                        password = ApiClient.hashPassword(password)
                    )
                )
            }

            // 1. Send to remote MariaDB via OkHttp
            val remoteSuccess = withContext(Dispatchers.IO) {
                ApiClient.createUser(id = id, username = username, email = email, password = password)
            }

            if (!remoteSuccess) {
                _authState.value = AuthState.Error("Não foi possível se conectar com o servidor")
                return@launch
            }



            _authState.value = AuthState.Success
        }
    }

    // LOGIN — checks local Room DB
    fun login(username: String, email: String, password: String) {
        if (username.isBlank() || password.isBlank() || email.isBlank()) {
            _authState.value = AuthState.Error("Preencha todos os campos")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val hashedPassword = ApiClient.hashPassword(password)
            val user = withContext(Dispatchers.IO) {
                db.userDao().login(username, hashedPassword)
            }
            _authState.value = if (user != null) AuthState.Success
            else AuthState.Error("Usúário ou senha inválidos")
        }
    }
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}