// src/main/java/com/example/fiulostandfound/AddItemActivity.kt
package com.example.fiulostandfound

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons                            // from material-icons-extended
import androidx.compose.material.icons.filled.ArrowBack              // the back arrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiulostandfound.data.Item                         // your data model
import com.example.fiulostandfound.ui.AddItemForm
import com.example.fiulostandfound.ui.ItemViewModel
import com.example.fiulostandfound.ui.theme.FIULostAndFoundTheme

class AddItemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mode = intent.getStringExtra("mode") ?: "lost"
        setContent {
            FIULostAndFoundTheme {
                AddItemScreen(
                    mode     = mode,
                    onSubmit = { finish() },
                    onCancel = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    mode: String,
    onSubmit: (Item) -> Unit,
    onCancel: () -> Unit,
    viewModel: ItemViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (mode == "lost") "Report Lost Item"
                        else                     "Report Found Item"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector      = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        AddItemForm(
            modifier = Modifier.padding(padding),
            onSubmit = { newItem ->
                if (mode == "lost") viewModel.addLost(newItem)
                else               viewModel.addFound(newItem)
                onSubmit(newItem)
            }
        )
    }
}
