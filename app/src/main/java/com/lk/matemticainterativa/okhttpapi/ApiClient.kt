package com.lk.matemticainterativa.okhttpapi

import com.google.gson.Gson
import com.lk.matemticainterativa.data.local.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.security.MessageDigest
import java.util.UUID

object ApiClient {
    private val client = OkHttpClient()
//    private const val BASE_URL = "http://172.31.59.52:3000" //rede prefeitura
    private const val BASE_URL = "http://192.168.0.78:3000" //casa
//    private const val BASE_URL = "http://10.173.218.74:3000" //android USB anchor

    suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        val request = Request.Builder()
            .url("$BASE_URL/users")
            .build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: "[]"
            Gson().fromJson(body, Array<User>::class.java).toList()
        }
    }
    // Hash password with SHA-256 before sending
    fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun createUser(
        id: UUID,
        username: String,
        email: String,
        password: String  // plain text — will be hashed here
    ): Boolean {
        return try {
            val json = Gson().toJson(
                mapOf(
                    "id" to id.toString(),
                    "username" to username,
                    "email" to email,
                    "password" to password
                )
            )
            val body = json.toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url("$BASE_URL/users")
                .post(body)
                .build()

            client.newCall(request).execute().use { it.isSuccessful }
        } catch (e: Exception) {
            false
        }
    }
    /** Returns true if server authenticated the user, null if unreachable. */
    fun loginUser(username: String, email: String, password: String): Boolean? {
        return try {
            val json = Gson().toJson(
                mapOf("username" to username, "email" to email, "password" to password)
            )
            val body = json.toRequestBody("application/json".toMediaType())
            val request = Request.Builder()
                .url("$BASE_URL/login")
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                when {
                    response.isSuccessful -> true
                    response.code == 401 -> false
                    else -> null // server error — treat as unreachable
                }
            }
        } catch (e: Exception) {
            null // network unreachable
        }
    }
}
