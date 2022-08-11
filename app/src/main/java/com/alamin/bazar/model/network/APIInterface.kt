package com.alamin.bazar.model.network

import com.alamin.bazar.model.data.UserResponse
import com.alamin.bazar.model.data.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIInterface {

    @POST("api/user")
    suspend fun createUser(@Body user: UserData): Response<UserResponse>
}