package com.alamin.bazar.model.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CheckoutHolder (
    val products: List<Checkout>
    ) : Parcelable