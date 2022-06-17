package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class UserRegistration(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("pic")
    val pic: String
)