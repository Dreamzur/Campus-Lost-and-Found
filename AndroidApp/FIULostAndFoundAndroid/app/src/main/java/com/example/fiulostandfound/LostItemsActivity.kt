package com.example.fiulostandfound

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.fiulostandfound.ui.theme.FIUTheme
import com.example.fiulostandfound.ui.GridScreen
import com.example.fiulostandfound.ui.ItemViewModel

class LostItemsActivity : ComponentActivity() {
    private val viewModel by viewModels<ItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAll()

        setContent {
            FIUTheme {
                val items by viewModel.lostItems.collectAsState()
                val ctx = LocalContext.current
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                ) {
                    GridScreen(
                        title = "Lost Items",
                        itemsList = viewModel.lostItems.collectAsState().value,
                        onItemClick = { item ->
                            ctx.startActivity(Intent(ctx, ItemDetailActivity::class.java).apply {
                                putExtra("item", item)
                            }
                            )
                        },
                    )
                }
            }
        }
    }
}

