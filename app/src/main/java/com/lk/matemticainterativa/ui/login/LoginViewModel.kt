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



    // REGISTER — sends to remote API + saves locally
    fun register(username: String, email: String, password: String) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Fill in all fields")
            return
        }
        if (password.length < 6) {
            _authState.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading

            // 2. Save locally in Room (with hashed password)
            withContext(Dispatchers.IO) {
                db.userDao().insertUser(
                    User(
                        id = UUID.randomUUID(),
                        username = username,
                        email = email,
                        password = ApiClient.hashPassword(password)
                    )
                )
            }

            // 1. Send to remote MariaDB via OkHttp
            val remoteSuccess = withContext(Dispatchers.IO) {
                ApiClient.createUser(username = username, email = email, password = password)
            }

            if (!remoteSuccess) {
                _authState.value = AuthState.Error("Could not connect to server")
                return@launch
            }



            _authState.value = AuthState.Success
        }
    }

    // LOGIN — checks local Room DB
    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _authState.value = AuthState.Error("Fill in all fields")
            return
        }

        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val hashedPassword = ApiClient.hashPassword(password)
            val user = withContext(Dispatchers.IO) {
                db.userDao().login(username, hashedPassword)
            }
            _authState.value = if (user != null) AuthState.Success
            else AuthState.Error("Invalid username or password")
        }
    }
    fun resetState() {
        _authState.value = AuthState.Idle
    }
}