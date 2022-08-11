package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cart")
    val cart: List<CartProduct>
)