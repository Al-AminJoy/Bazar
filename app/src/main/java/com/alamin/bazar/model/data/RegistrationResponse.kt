package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pic")
    val pic: String,
    @SerializedName("token")
    val token: String
)