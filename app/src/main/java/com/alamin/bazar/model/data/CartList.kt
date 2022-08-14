package com.alamin.bazar.model.data


import com.google.gson.annotations.SerializedName

data class CartList(
    @SerializedName("cart")
    val cart: List<Cart>
)