package com.lk.matemticainterativa.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: UUID,
    val username: String,
    val password: String, //delete password
    val email: String
)
