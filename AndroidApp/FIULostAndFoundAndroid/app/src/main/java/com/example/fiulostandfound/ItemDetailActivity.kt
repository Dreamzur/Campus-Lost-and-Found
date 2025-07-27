// src/main/java/com/example/fiulostandfound/ItemDetailActivity.kt
package com.example.fiulostandfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.fiulostandfound.data.Item
import com.example.fiulostandfound.ui.ItemDetailScreen
import com.example.fiulostandfound.ui.ItemViewModel
import com.example.fiulostandfound.ui.theme.FIULostAndFoundTheme

class ItemDetailActivity : ComponentActivity() {
    private val viewModel: ItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val item = intent
            .getParcelableExtra<Item>("item")
            ?: run {
                // if no item, just close
                finish()
                return
            }


        val mode = intent.getStringExtra("mode") ?: "lost"

        setContent {
            FIULostAndFoundTheme {
                ItemDetailScreen(item = item) {

                    if (item.id != null) {
                        if (mode == "lost") {
                            viewModel.claimLost(item.id)
                        } else {
                            viewModel.claimFound(item.id)
                        }
                    }
                    finish()
                }
            }
        }
    }
}