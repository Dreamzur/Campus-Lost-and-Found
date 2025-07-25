package com.example.fiulostandfound.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiulostandfound.data.Item
import com.example.fiulostandfound.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ItemViewModel : ViewModel() {
    private val _lostItems  = MutableStateFlow<List<Item>>(emptyList())
    val  lostItems: StateFlow<List<Item>> = _lostItems

    private val _foundItems = MutableStateFlow<List<Item>>(emptyList())
    val  foundItems: StateFlow<List<Item>> = _foundItems

    private val _errorMessage = MutableStateFlow<String?>(null)
    val  errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadAll()
    }

    /** Fetch *both* lost and found lists in one shot */
    fun loadAll() = viewModelScope.launch {
        // LOST
        try {
            val lostResp = RetrofitClient.api.getLost()
            if (lostResp.isSuccessful) {
                _lostItems.value = lostResp.body().orEmpty()
                _errorMessage.value = null
            } else {
                _errorMessage.value = "Failed to load lost items: ${lostResp.code()} / ${lostResp.message()}"
                Log.e("ItemVM", _errorMessage.value!!)
            }
        } catch (e: IOException) {
            _errorMessage.value = "Network error (lost): ${e.localizedMessage}"
            Log.e("ItemVM", "loadAll() lost error", e)
        } catch (e: HttpException) {
            _errorMessage.value = "HTTP error (lost): ${e.code()} ${e.message}"
            Log.e("ItemVM", "loadAll() lost error", e)
        }

        // FOUND
        try {
            val foundResp = RetrofitClient.api.getFound()
            if (foundResp.isSuccessful) {
                _foundItems.value = foundResp.body().orEmpty()
                _errorMessage.value = null
            } else {
                _errorMessage.value = "Failed to load found items: ${foundResp.code()} / ${foundResp.message()}"
                Log.e("ItemVM", _errorMessage.value!!)
            }
        } catch (e: IOException) {
            _errorMessage.value = "Network error (found): ${e.localizedMessage}"
            Log.e("ItemVM", "loadAll() found error", e)
        } catch (e: HttpException) {
            _errorMessage.value = "HTTP error (found): ${e.code()} ${e.message}"
            Log.e("ItemVM", "loadAll() found error", e)
        }
    }

    fun addLost(item: Item) = viewModelScope.launch {
        try {
            val postResp = RetrofitClient.api.postLost(item)
            if (postResp.isSuccessful) {
                // reload only lost
                val resp = RetrofitClient.api.getLost()
                if (resp.isSuccessful) _lostItems.value = resp.body().orEmpty()
                else                   _errorMessage.value = "Refresh lost failed: ${resp.code()}"
            } else {
                _errorMessage.value = "Post lost failed: ${postResp.code()}"
            }
        } catch (e: IOException) {
            _errorMessage.value = "Network error (postLost): ${e.localizedMessage}"
        } catch (e: HttpException) {
            _errorMessage.value = "HTTP error (postLost): ${e.code()} ${e.message}"
        }
    }

    suspend fun postLostBlocking(item: Item): Boolean {
        return try {
            val resp = RetrofitClient.api.postLost(item)
            if (!resp.isSuccessful) {
                Log.e("ItemVM", "postLost failed: ${resp.code()} / ${resp.errorBody()?.string()}")
            }
            resp.isSuccessful
        } catch (e: Exception) {
            Log.e("ItemVM", "postLost exception", e)
            false
        }
    }


    suspend fun postFoundBlocking(item: Item): Boolean {
        return try {
            val resp = RetrofitClient.api.postFound(item)
            if (!resp.isSuccessful) {
                Log.e("ItemVM", "postFound failed: ${resp.code()} / ${resp.errorBody()?.string()}")
            }
            resp.isSuccessful
        } catch (e: Exception) {
            Log.e("ItemVM", "postFound exception", e)
            false
        }
    }




    fun addFound(item: Item) = viewModelScope.launch {
        try {
            val postResp = RetrofitClient.api.postFound(item)
            if (postResp.isSuccessful) {
                // reload only found
                val resp = RetrofitClient.api.getFound()
                if (resp.isSuccessful) _foundItems.value = resp.body().orEmpty()
                else                   _errorMessage.value = "Refresh found failed: ${resp.code()}"
            } else {
                _errorMessage.value = "Post found failed: ${postResp.code()}"
            }
        } catch (e: IOException) {
            _errorMessage.value = "Network error (postFound): ${e.localizedMessage}"
        } catch (e: HttpException) {
            _errorMessage.value = "HTTP error (postFound): ${e.code()} ${e.message}"
        }
    }
}
