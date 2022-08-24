package com.alamin.bazar.model.data


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Name(
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("lastname")
    val lastname: String
):Parcelable