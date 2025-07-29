package com.example.fiulostandfound

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fiulostandfound.ui.*
import com.example.fiulostandfound.ui.theme.FIULostAndFoundTheme


class MainActivity : ComponentActivity() {
    private val itemViewModel     by viewModels<ItemViewModel>()
    private val loginViewModel    by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FIULostAndFoundTheme {
                var loggedIn    by rememberSaveable { mutableStateOf(false) }
                var registering by rememberSaveable { mutableStateOf(false) }
                val role   by loginViewModel.role.collectAsState(initial = null)
                val isAdmin = role == "admin"
                val context = LocalContext.current

                LaunchedEffect(loggedIn) {
                    if (loggedIn) itemViewModel.loadAll()
                }

                when {
                    !loggedIn && registering -> {
                        RegisterScreen(
                            onRegistered  = { registering = false },
                            onBack        = { registering = false }
                        )
                    }

                    !loggedIn -> {
                        LoginScreen(
                            viewModel        = loginViewModel,
                            onLoggedIn       = { loggedIn = true },
                            onRegisterClick  = { registering = true }
                        )
                    }

                    else -> {
                        HomeScreen(
                            lostItems = itemViewModel.lostItems.collectAsState().value,
                            foundItems = itemViewModel.foundItems.collectAsState().value,


                            onLostClick = {
                                startActivity(
                                    Intent(
                                        this,
                                        LostItemsActivity::class.java
                                    )
                                        .putExtra("isAdmin", isAdmin)
                                )
                            },
                            onFoundClick = {
                                startActivity(
                                    Intent(
                                        this,
                                        FoundItemsActivity::class.java
                                    )
                                        .putExtra("isAdmin", isAdmin)
                                )
                            },
                            onReportLostClick = {
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
                            },

                            onItemClick = { item ->
                                // build an Intent with the parcelable item
                                val intent = Intent(this, ItemDetailActivity::class.java).apply {
                                    putExtra("item", item)
                                    putExtra("mode", "lost")
                                    putExtra("isAdmin",  isAdmin )
                                }
                                startActivity(intent)
                            },

                            reportFoundVisible = isAdmin,
                        )
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        itemViewModel.loadAll()
    }

}

