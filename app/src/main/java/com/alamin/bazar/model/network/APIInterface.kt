package com.alamin.bazar.model.network

import com.alamin.bazar.model.data.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface APIInterface {

    @POST("auth/login")
    fun login(@Body user: UserData): Observable<Response<UserResponse>>

    @GET("users/{id}")
    fun getUser(@Path(value = "id") id : Int): Observable<Response<User>>

    @POST("users")
    fun signup(@Body user: User): Observable<Response<User>>

    @POST("carts")
    fun addCart(@Body cart: Cart): Observable<Response<Cart>>

    @GET("products")
    fun getProducts(): Observable<Response<List<Product>>>

    @PATCH("users/{id}")
    fun updateUser(@Path("id") id: Int,@Body user: User): Observable<Response<User>>
}