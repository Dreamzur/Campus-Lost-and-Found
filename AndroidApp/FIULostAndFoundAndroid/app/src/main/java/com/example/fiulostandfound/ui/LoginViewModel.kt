package com.example.fiulostandfound.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiulostandfound.data.LoginRequest
import com.example.fiulostandfound.data.RetrofitClient
import com.example.fiulostandfound.data.UserPrefs
import kotlinx.coroutines.launch

class LoginViewModel(private val ctx: Application) : AndroidViewModel(ctx) {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMsg by mutableStateOf<String?>(null)

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            errorMsg = null
            try {
                val resp = RetrofitClient.api.login(
                    LoginRequest(username, password)
                )
                if (resp.isSuccessful) {
                    resp.body()?.token?.let { token ->
                        UserPrefs.saveToken(ctx, token)
                        onSuccess()
                    } ?: run {
                        errorMsg = "Empty response"
                    }
                } else {
                    errorMsg = "Login failed: ${resp.code()}"
                }
            } catch (e: Exception) {
                errorMsg = e.localizedMessage
            }
            isLoading = false
        }
    }
}