package com.lk.matemticainterativa.data.repository

import com.lk.matemticainterativa.data.local.User
import com.lk.matemticainterativa.data.local.UserDao

class UserRepository(private val dao: UserDao) {
    suspend fun register(user: User) = dao.insertUser(user)
    suspend fun login(username: String, password: String) = dao.login(username, password)

}