package com.example.fiulostandfound

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.fiulostandfound.ui.theme.FIUTheme
import com.example.fiulostandfound.ui.GridScreen
import com.example.fiulostandfound.ui.ItemViewModel
import com.example.fiulostandfound.ui.LoginViewModel
import kotlin.properties.ReadOnlyProperty



class FoundItemsActivity : ComponentActivity() {
    private val viewModel by viewModels<ItemViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isAdmin = intent.getBooleanExtra("isAdmin", false)

        setContent {
            FIUTheme {
                FoundItemsScreen(
                    viewModel = viewModel,
                    isAdmin   = isAdmin
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadAll()
    }
}


@Composable
fun FoundItemsScreen(viewModel: ItemViewModel, isAdmin: Boolean) {

    LaunchedEffect(Unit) {
        viewModel.loadAll()
    }


    val allFound by viewModel.foundItems.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }


    val filtered = remember(allFound, query) {
        if (query.isBlank()) {
            allFound
        } else {
            allFound.filter { item ->

                val matchesTitle = item.title
                    ?.contains(query, ignoreCase = true)
                    ?: false


                val matchesDesc = item.description
                    ?.contains(query, ignoreCase = true)

                matchesTitle || matchesDesc == true
            }
        }
    }

    val ctx = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search by title or description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { /* you can hide keyboard here */ })
        )

        Spacer(Modifier.height(16.dp))

        GridScreen(
            title     = "Found Items",
            itemsList = filtered,
            onItemClick = { item ->
                ctx.startActivity(
                    Intent(ctx, ItemDetailActivity::class.java).apply {
                        putExtra("item", item)
                        putExtra("mode", "lost")
                        putExtra("isAdmin", isAdmin)
                    }
                )
            }
        )
    }
}