package com.alamin.bazar.model.data

data class Checkout(
    val productId: Int,
    val quantity: Int,
    val title: String,
    val category: String,
    val image: String,
    val price: Double
)
