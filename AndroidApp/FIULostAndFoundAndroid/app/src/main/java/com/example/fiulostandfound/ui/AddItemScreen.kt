package com.example.fiulostandfound.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiulostandfound.FiuLostAndFound
import com.example.fiulostandfound.data.Item
import com.example.fiulostandfound.data.RetrofitClient
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    mode: String,
    onDone: () -> Unit,
    viewModel: com.example.fiulostandfound.ui.ItemViewModel = viewModel()
) {
    val api = FiuLostAndFound.api
    val scope = rememberCoroutineScope()
    var isSubmitting by remember { mutableStateOf(false) }
    var error       by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (mode == "lost") "Report Lost Item" else "Report Found Item") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            AddItemForm(
                modifier = Modifier.fillMaxWidth(),
                onSubmit = { newItem: Item ->
                    isSubmitting = true
                    error = null
                    scope.launch {
                        try {
                            val resp = if (mode == "lost")
                                api.postLost(newItem)
                            else
                                api.postFound(newItem)

                            if (resp.isSuccessful) {
                                onDone()
                            } else {
                                val body = resp.errorBody()?.string() ?: "no body"
                                error = "Error ${resp.code()}: $body"
                            }
                        } catch (e: Exception) {
                            error = e.toString()
                        } finally {
                            isSubmitting = false
                        }
                    }
                }
            )

            if (isSubmitting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                )
            }

            error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}



