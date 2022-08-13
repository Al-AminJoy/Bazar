package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String
)