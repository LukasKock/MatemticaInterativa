package com.lk.matemticainterativa.ui.components.similarTriangles

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.lk.matemticainterativa.ui.components.questionfeedback.QuestionFeedbackPopup

@Composable
fun YesOrNoButtons(areTrianglesSimilar: Boolean,
                   explanationCorrect: String,
                   explanationFalse: String,
                   visible: Boolean,
                   onFinished: @Composable () -> Unit) {

    if(!visible) return

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    var showFeedback by remember { mutableStateOf(false) }
    var isUserAnswerCorrect by remember { mutableStateOf(false) }
    var wasDialogDismissed by remember { mutableStateOf(false) }

    val isDark = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val buttons: @Composable (Modifier) -> Unit = { buttonModifier ->
            Button(
                modifier = buttonModifier,
                onClick = {
                    if(areTrianglesSimilar) isUserAnswerCorrect = true else false
                    showFeedback = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDark) colorScheme.primary else Color(0xFF2585D3),
                    contentColor = colorScheme.onPrimary
                )
            ) { Text("Sim") }

            Button(
                modifier = buttonModifier,
                onClick = {
                    if(!areTrianglesSimilar) isUserAnswerCorrect = true else false
                    showFeedback = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isDark) colorScheme.primary else Color(0xFF009688),
                    contentColor = colorScheme.onPrimary
                )
            ) { Text("Não") }
        }

        if (isLandscape) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .navigationBarsPadding(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                buttons(Modifier)
            }
        } else {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                buttons(Modifier.weight(1f))
            }
        }
    }
    if (showFeedback) {
        QuestionFeedbackPopup(
            isCorrect = isUserAnswerCorrect,
            onDismiss = { showFeedback = false
                wasDialogDismissed = true },
            explanation = if (isUserAnswerCorrect) explanationCorrect else explanationFalse)
    }
    if(wasDialogDismissed) onFinished()
}