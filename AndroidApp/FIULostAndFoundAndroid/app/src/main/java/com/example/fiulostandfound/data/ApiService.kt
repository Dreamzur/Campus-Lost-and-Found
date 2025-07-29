
package com.example.fiulostandfound.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.example.fiulostandfound.data.Item
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("/api/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @POST("/api/register")
    suspend fun register(@Body req: RegisterRequest): AuthResponse
    @GET("/api/lost")
    suspend fun getLost(): Response<List<Item>>
    @GET("/api/found")
    suspend fun getFound(): Response<List<Item>>
    @POST("/api/lost")
    suspend fun postLost(@Body item: Item): Response<Item>
    @POST("/api/found")
    suspend fun postFound(@Body item: Item): Response<Item>

    @PUT("api/lost/{id}/claim")
    suspend fun claimLost(@Path("id") id: Long): Response<Unit>

    @PUT("api/found/{id}/claim")
    suspend fun claimFound(@Path("id") id: Long): Response<Unit>

    @DELETE("api/lost/{id}")
    suspend fun deleteLost(@Path("id") id: Long): Response<Unit>

    @DELETE("api/found/{id}")
    suspend fun deleteFound(@Path("id") id: Long): Response<Unit>

}
