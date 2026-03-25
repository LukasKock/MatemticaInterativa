package com.lk.matemticainterativa.okhttpapi

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

// ApiClient.kt
object ApiClient {
    private val client = OkHttpClient()
    private const val BASE_URL = "http://YOUR_SERVER_IP:3000"

    suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("$BASE_URL/users")
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: "[]"
            Gson().fromJson(body, Array<User>::class.java).toList()
        }
    }

    suspend fun createUser(name: String, email: String): Boolean = withContext(Dispatchers.IO) {
        val json = Gson().toJson(mapOf("name" to name, "email" to email))
        val body = json.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("$BASE_URL/users")
            .post(body)
            .build()

        client.newCall(request).execute().use { it.isSuccessful }
    }
}

// User.kt
data class User(val id: Int, val name: String, val email: String)