package com.lk.matemticainterativa.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.lk.matemticainterativa.data.local.AppDataBase
import com.lk.matemticainterativa.data.local.User
import com.lk.matemticainterativa.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(app: Application) : AndroidViewModel(app) {
    private val db = Room.databaseBuilder(
        app, AppDataBase::class.java, "user_db"
    ).build()
    private val repo = UserRepository(db.userDao())

    var loggedIn = false
        private set

    fun register(username: String, password: String){
        viewModelScope.launch {
            repo.register(User(username = username, password = password))
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit ){
        viewModelScope.launch {
            val user = repo.login(username, password)
            loggedIn = user != null
            onResult(loggedIn)
        }
    }
}