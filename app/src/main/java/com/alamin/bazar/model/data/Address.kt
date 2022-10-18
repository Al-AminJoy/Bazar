package com.alamin.bazar.model.data


import android.os.Parcelable
import androidx.room.Embedded
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Address @JvmOverloads constructor(
    @SerializedName("city")
    val city: String,
    @SerializedName("geolocation")
    @Embedded
    val geolocation: @RawValue Geolocation,
    @SerializedName("number")
    val number: Int,
    @SerializedName("street")
    val street: String,
    @SerializedName("zipcode")
    val zipcode: String
): Parcelable