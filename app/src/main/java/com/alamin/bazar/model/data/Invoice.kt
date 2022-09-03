package com.alamin.bazar.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity
@Parcelize
data class Invoice(
    @PrimaryKey(autoGenerate = true)
    val invoiceId: Int,
    val invoiceDate: String,
    val subTotal: Double,
    val shippingCharge: Double,
    val total: Double,
    val address: String,
    val isCashOnDelivery: Boolean,
    val note: String,
    var status: String,
    val isReceived: Boolean,
    val checkoutHolder: @RawValue CheckoutHolder
):Parcelable
