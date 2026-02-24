package com.lk.matemticainterativa.ui.components.questionfeedback

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun QuestionFeedbackPopup(
    isCorrect: Boolean,
    onDismiss: () -> Unit,
    explanation: String
) {
    val isDark = isSystemInDarkTheme()

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(if(isDark){
                        if (isCorrect) Color(0xFF071307) else Color(0xFF1E0909)
                    }else{
                        if (isCorrect) Color(0xFFF0FDF4)else Color(0xFFFEF2F2)
                    }
                    )
                    .padding(24.dp)
            ) {
                // Header with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (isCorrect) 
                            Icons.Default.CheckCircle
                        else 
                            Icons.Default.Clear,
                        contentDescription = null,
                        tint = if (isCorrect) 
                            Color(0xFF22C55E) 
                        else 
                            Color(0xFFEF4444),
                        modifier = Modifier.size(48.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    Text(
                        text = if (isCorrect) "Correto!" else "Errado",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = if(isDark){
                            if (isCorrect) Color(0xFF88DA78) else Color(0xFFEE7979)
                        } else{
                            if (isCorrect) Color(0xFF22C55E)else Color(0xFFEF4444)
                        }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Explanation
                Text(
                    text = explanation,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if(isDark) Color(0xFFE0E0E0) else Color(0xFF374151)
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Continue button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCorrect) 
                            Color(0xFF22C55E) 
                        else 
                            Color(0xFF6366F1)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Continuar",
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

// Usage in your question screen
@Composable
fun QuestionScreen() {
    var showFeedback by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf(false) }
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Is the Earth flat?", style = MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                isAnswerCorrect = false
                showFeedback = true
            }
        ) {
            Text("Right")
        }
        
        Button(
            onClick = {
                isAnswerCorrect = true
                showFeedback = true
            }
        ) {
            Text("Wrong")
        }
    }
    
    if (showFeedback) {
        QuestionFeedbackPopup(
            isCorrect = isAnswerCorrect,
            onDismiss = { showFeedback = false },
            explanation = if (isAnswerCorrect) 
                "Great job! The Earth is not flat." 
            else 
                "Not quite. The Earth is actually an oblate spheroid."
        )
    }
}