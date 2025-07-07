package com.example.fiulostandfound

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.fiulostandfound.data.Item
import com.example.fiulostandfound.ui.theme.FIUTheme
import com.example.fiulostandfound.ui.HomeScreen
import com.example.fiulostandfound.ui.ItemViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<ItemViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // kick off loading from the server
        viewModel.loadAll()

        setContent {
            FIUTheme {
                HomeScreen(
                    lostItems   = viewModel.lostItems.collectAsState().value,
                    foundItems  = viewModel.foundItems.collectAsState().value,
                    onLostClick = { startActivity(Intent(this, LostItemsActivity::class.java)) },
                    onFoundClick= { startActivity(Intent(this, FoundItemsActivity::class.java)) },
                    onReportLostClick  = {
                        startActivity(
                            Intent(this, AddItemActivity::class.java)
                                .putExtra("mode", "lost")
                        )
                    },
                    onReportFoundClick = {
                        startActivity(
                            Intent(this, AddItemActivity::class.java)
                                .putExtra("mode", "found")
                        )
                    })
            }
        }
    }
}
