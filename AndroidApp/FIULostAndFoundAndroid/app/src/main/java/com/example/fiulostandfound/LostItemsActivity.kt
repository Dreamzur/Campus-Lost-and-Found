package com.example.fiulostandfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fiulostandfound.data.Item
import com.example.fiulostandfound.ui.AddItemForm
import com.example.fiulostandfound.ui.theme.FIUTheme
import com.example.fiulostandfound.ui.GridScreen
import com.example.fiulostandfound.ui.ItemViewModel

class LostItemsActivity : ComponentActivity() {
    private val viewModel by viewModels<ItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAll()  // fetch from backend

        setContent {
            FIUTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                ) {
                    GridScreen(
                        title = "Lost Items",
                        itemsList = viewModel.lostItems.collectAsState().value
                    )
                }
            }
        }
    }
}
