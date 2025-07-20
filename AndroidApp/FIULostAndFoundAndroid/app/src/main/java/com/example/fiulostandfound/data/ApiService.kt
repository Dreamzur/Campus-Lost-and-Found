// app/src/main/java/com/example/fiulostandfound/data/ApiService.kt
package com.example.fiulostandfound.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.fiulostandfound.data.Item
import retrofit2.Response

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @POST("/api/register")
    suspend fun register(@Body req: RegisterRequest): AuthResponse
    @GET("api/lost")
    suspend fun getLost(): Response<List<Item>>
    @GET("api/found")
    suspend fun getFound(): Response<List<Item>>
    @POST("api/lost")
    suspend fun postLost(@Body item: Item): Response<Item>
    @POST("api/found")
    suspend fun postFound(@Body item: Item): Response<Item>
}
