package com.example.fiulostandfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fiulostandfound.ui.theme.FIULostAndFoundTheme
import com.example.fiulostandfound.data.Item

class FoundItemsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FIULostAndFoundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ItemList2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemList2(name: String, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        GridCells.Adaptive(200.dp),

        ) {
        item(span = { GridItemSpan(this.maxLineSpan) }) {

        }
        items(10) { index ->
            Column {
                Text(
                    text = "Item $index",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview3() {
        FIULostAndFoundTheme {
            ItemList2("Android")
        }
    }
