
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

    fun register(onRegistered: ERROR, password1: Any, confirmPassword1: Any, function: () -> Unit) {
        if (password != confirmPassword) {
            _errorMsg.value = "Passwords do not match"
            return
        }
        _errorMsg.value = null
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val resp = RetrofitClient
                    .api
                    .register(RegisterRequest(username, password))
                onRegistered(resp.token)
            } catch (e: Exception) {
                _errorMsg.value = "Registration failed: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
