package com.alamin.bazar.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Checkout(
    val productId: Int,
    val quantity: Int,
    val title: String,
    val category: String,
    val image: String,
    val price: Double
):Parcelable
