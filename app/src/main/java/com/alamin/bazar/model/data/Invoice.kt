package com.alamin.bazar.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Invoice(
    val invoiceId: Int,
    val invoiceDate: String,
    val subTotal: Double,
    val shippingCharge: Double,
    val total: Double,
    val address: String,
    val isCashOnDelivery: Boolean,
    val note: String,
    val products: List<Checkout>
):Parcelable
