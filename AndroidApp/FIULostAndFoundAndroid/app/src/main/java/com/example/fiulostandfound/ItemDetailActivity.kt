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

        val item   = intent.getParcelableExtra<Item>("item") ?: run { finish(); return }
        val mode   = intent.getStringExtra("mode") ?: "lost"
        val isAdmin= intent.getBooleanExtra("isAdmin", false)

        setContent {
            FIULostAndFoundTheme {
                ItemDetailScreen(
                    item = item,
                    isAdmin = isAdmin,
                    onClaim = {
                        item.id?.let { id ->
                            if (mode == "lost") viewModel.claimLost(id)
                            else               viewModel.claimFound(id)
                        }
                        finish()
                    },
                    onRemove = {
                        item.id?.let { id ->
                            if (mode == "lost") viewModel.removeLost(id)
                            else               viewModel.removeFound(id)
                        }
                        finish()
                    }
                )
            }
        }
    }
}

