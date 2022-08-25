package com.alamin.bazar.model.data


import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User(
    @SerializedName("address")
    @Embedded
    val address: @RawValue Address,
    @SerializedName("email")
    val email: String,
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    @Embedded
    val name: @RawValue Name,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String,
): Parcelable