package com.alamin.bazar.model.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Geolocation(
    @SerializedName("lat")
    var lat: String,
    @SerializedName("long")
    var longi: String
) : Parcelable