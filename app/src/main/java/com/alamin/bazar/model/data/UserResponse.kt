package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("token")
    val token: String
)