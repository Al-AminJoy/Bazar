package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("date")
    val date: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("products")
    val products: List<CartProduct>
)