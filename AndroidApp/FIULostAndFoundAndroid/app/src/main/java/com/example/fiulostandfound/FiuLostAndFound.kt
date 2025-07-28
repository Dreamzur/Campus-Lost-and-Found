package com.example.fiulostandfound

import android.app.Application
import com.example.fiulostandfound.data.ApiService
import com.example.fiulostandfound.data.RetrofitClient

class FiuLostAndFound : Application() {
    companion object {
        lateinit var api: ApiService
            private set
    }

    override fun onCreate() {
        super.onCreate()
        // applicationContext is safe to hold forever
        api = RetrofitClient.create(applicationContext)
    }
}


