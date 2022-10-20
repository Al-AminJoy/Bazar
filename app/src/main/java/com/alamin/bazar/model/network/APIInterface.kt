package com.alamin.bazar.model.network

import com.alamin.bazar.model.data.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface APIInterface {

    @POST("auth/login")
    suspend fun login(@Body user: UserData): Response<UserResponse>

    @GET("users/{id}")
    suspend fun getUser(@Path(value = "id") id : Int): Response<User>

    @POST("users/signup")
    suspend fun signup(@Body user: UserData): Response<UserResponse>

    @POST("carts")
    suspend fun addCart(@Body cart: Cart): Response<Cart>

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @PATCH("users/{id}")
    suspend fun updateUser(@Path("id") id: Int,@Body user: User): Response<User>
}