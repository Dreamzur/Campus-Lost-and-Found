package com.example.fiulostandfound

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import com.example.fiulostandfound.ui.*
import com.example.fiulostandfound.ui.theme.FIULostAndFoundTheme

class MainActivity : ComponentActivity() {
    private val itemViewModel     by viewModels<ItemViewModel>()
    private val loginViewModel    by viewModels<LoginViewModel>()
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FIULostAndFoundTheme {
                var loggedIn    by rememberSaveable { mutableStateOf(false) }
                var registering by rememberSaveable { mutableStateOf(false) }

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
                        // Main home UI once authenticated
                        HomeScreen(
                            lostItems          = itemViewModel.lostItems.collectAsState().value,
                            foundItems         = itemViewModel.foundItems.collectAsState().value,
                            onLostClick        = { startActivity(Intent(this, LostItemsActivity::class.java)) },
                            onFoundClick       = { startActivity(Intent(this, FoundItemsActivity::class.java)) },
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
                            }
                        )
                    }
                }
            }
        }
    }
}
