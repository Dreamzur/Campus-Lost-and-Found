
package com.example.fiulostandfound.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiulostandfound.data.RegisterRequest
import com.example.fiulostandfound.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var username = ""
    var password = ""
    var confirmPassword = ""


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg = _errorMsg.asStateFlow()

    fun register( username: String,
                  password: String,
                  confirmPassword: String,
                  onResult: (Boolean) -> Unit
    ) {
        if (password != confirmPassword) {
            _errorMsg.value = "Passwords do not match"
            onResult(false)
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // your retrofit call, for example:
                RetrofitClient.api.register(RegisterRequest(username, password))
                onResult(true)
            } catch (e: Exception) {
                _errorMsg.value = e.message ?: "Registration failed"
                onResult(false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}