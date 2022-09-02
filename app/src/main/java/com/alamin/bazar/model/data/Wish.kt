package com.alamin.bazar.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val wishId: Int,
    val productId: Int)
