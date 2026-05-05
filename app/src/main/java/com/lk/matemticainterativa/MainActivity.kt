package com.lk.matemticainterativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.lk.matemticainterativa.ui.navigation.AppNav

class MainActivity : ComponentActivity() {
    companion object {
        const val numberOfTriangleActivities = 5
        const val numberOfVectorActivities = 4

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                AppNav()
            }
        }
    }
}

