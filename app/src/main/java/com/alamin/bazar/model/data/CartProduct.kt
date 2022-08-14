package com.alamin.bazar.model.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class CartProduct(
    @PrimaryKey
    @SerializedName("productId")
    val productId: Int,
    @SerializedName("quantity")
    val quantity: Int
): Parcelable