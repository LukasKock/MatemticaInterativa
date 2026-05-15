package com.lk.matemticainterativa.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lk.matemticainterativa.R

@Composable
fun MenuItem(title: String, onClick: () -> Unit) {

    val isDark = isSystemInDarkTheme()

    val containerColor = if (isDark) Color.Black else Color.White
    val textColor = if (isDark) Color.White else MaterialTheme.colorScheme.onPrimaryContainer


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(modifier = Modifier.padding(16.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = title,
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                color = textColor
            )
            if(isDark){
                Icon(painter = painterResource(R.drawable.icon_check_dark_theme),
                    contentDescription = null,
                    tint = Color.Unspecified)
            } else {
                Icon(painter = painterResource(R.drawable.icon_check_light_theme),
                    contentDescription = null,
                    tint = Color.Unspecified)
            }
        }
    }
}