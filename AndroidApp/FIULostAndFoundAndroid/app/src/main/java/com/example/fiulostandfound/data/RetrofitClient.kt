package com.example.fiulostandfound.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.example.fiulostandfound.data.ApiService

object RetrofitClient {



    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}