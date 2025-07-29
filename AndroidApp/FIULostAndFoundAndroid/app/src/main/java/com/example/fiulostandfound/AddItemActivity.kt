// src/main/java/com/example/fiulostandfound/AddItemActivity.kt
package com.example.fiulostandfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fiulostandfound.ui.theme.FIULostAndFoundTheme
import com.example.fiulostandfound.ui.AddItemScreen


class AddItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mode = intent.getStringExtra("mode") ?: "lost"
        setContent {
            FIULostAndFoundTheme {
                AddItemScreen(
                    mode   = mode,
                    onDone = { finish() }
                )
            }
        }
    }
}
