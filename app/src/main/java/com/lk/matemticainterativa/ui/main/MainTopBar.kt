package com.lk.matemticainterativa.ui.main

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("Matemática Interativa") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = {
                        expanded = false
                        onSettingsClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Logout") },
                    onClick = {
                        expanded = false
                        onLogoutClick()
                    }
                )
            }
        }
    )
}
