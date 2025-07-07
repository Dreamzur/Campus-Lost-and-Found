package com.example.fiulostandfound.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiulostandfound.data.Item
import com.example.fiulostandfound.data.RetrofitClient   // ← imports ApiService.api
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ItemViewModel: ViewModel() {
    private val _lostItems  = MutableStateFlow<List<Item>>(emptyList())
    private val _foundItems = MutableStateFlow<List<Item>>(emptyList())
    val lostItems: StateFlow<List<Item>> = _lostItems
    val foundItems: StateFlow<List<Item>> = _foundItems

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadAll() {
        viewModelScope.launch {
            try {
                // 1) Load lost items
                val lostResp = RetrofitClient.api.getLost()
                if (lostResp.isSuccessful) {
                    _lostItems.value = lostResp.body().orEmpty()
                } else {
                    _errorMessage.value = "Error loading lost items: ${lostResp.code()} ${lostResp.message()}"
                }

                // 2) Load found items
                val foundResp = RetrofitClient.api.getFound()
                if (foundResp.isSuccessful) {
                    _foundItems.value = foundResp.body().orEmpty()
                } else {
                    _errorMessage.value = "Error loading found items: ${foundResp.code()} ${foundResp.message()}"
                }

            } catch (ioe: IOException) {
                _errorMessage.value = "Network error: ${ioe.localizedMessage}"
            } catch (he: HttpException) {
                _errorMessage.value = "HTTP error: ${he.code()} ${he.message()}"
            } catch (t: Throwable) {
                _errorMessage.value = "Unexpected error: ${t.localizedMessage}"
            }
        }
    }




    init {
        loadLostItems()
    }

    private fun loadLostItems() {
        viewModelScope.launch {
            try {
                val resp = RetrofitClient.api.getLost()
                if (resp.isSuccessful) {
                    _lostItems.value = resp.body().orEmpty()
                    _errorMessage.value = null
                } else {
                    // API returned 4xx or 5xx
                    _errorMessage.value = "Server error ${resp.code()}: ${resp.message()}"
                }
            } catch (e: IOException) {
                // network or conversion error
                _errorMessage.value = "Network error: ${e.localizedMessage}"
            } catch (e: HttpException) {
                // non-2xx but thrown anyway
                _errorMessage.value = "HTTP exception ${e.code()}: ${e.message}"
            } catch (e: Throwable) {
                _errorMessage.value = "Unexpected: ${e.localizedMessage}"
            }
        }
    }


    fun addLost(item: Item) = viewModelScope.launch {
        try {
            // 1) post the new item
            val postResp = RetrofitClient.api.postLost(item)
            if (postResp.isSuccessful) {
                // 2) if posting succeeded, reload the list
                val getResp = RetrofitClient.api.getLost()
                if (getResp.isSuccessful) {
                    _lostItems.value = getResp.body().orEmpty()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Failed to refresh lost items: ${getResp.code()} ${getResp.message()}"
                }
            } else {
                _errorMessage.value = "Failed to post lost item: ${postResp.code()} ${postResp.message()}"
            }
        } catch (ioe: IOException) {
            _errorMessage.value = "Network error: ${ioe.localizedMessage}"
        } catch (he: HttpException) {
            _errorMessage.value = "HTTP error: ${he.code()} ${he.message()}"
        } catch (t: Throwable) {
            _errorMessage.value = "Unexpected: ${t.localizedMessage}"
        }
    }

    fun addFound(item: Item) = viewModelScope.launch {
        try {
            val postResp = RetrofitClient.api.postFound(item)
            if (postResp.isSuccessful) {
                val getResp = RetrofitClient.api.getFound()
                if (getResp.isSuccessful) {
                    _foundItems.value = getResp.body().orEmpty()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = "Failed to refresh found items: ${getResp.code()} ${getResp.message()}"
                }
            } else {
                _errorMessage.value = "Failed to post found item: ${postResp.code()} ${postResp.message()}"
            }
        } catch (ioe: IOException) {
            _errorMessage.value = "Network error: ${ioe.localizedMessage}"
        } catch (he: HttpException) {
            _errorMessage.value = "HTTP error: ${he.code()} ${he.message()}"
        } catch (t: Throwable) {
            _errorMessage.value = "Unexpected: ${t.localizedMessage}"
        }
    }
}