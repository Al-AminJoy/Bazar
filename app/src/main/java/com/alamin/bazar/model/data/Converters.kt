package com.alamin.bazar.model.data

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun fromCheckoutHolder(checkoutHolder: CheckoutHolder): String{
        return Gson().toJson(checkoutHolder)
    }

    @TypeConverter
    fun toCheckoutHolder(checkoutHolder: String): CheckoutHolder{
        return Gson().fromJson(checkoutHolder,CheckoutHolder::class.java);
    }

    @TypeConverter
    fun fromGeoLocation(geolocation: Geolocation): String{
        return Gson().toJson(geolocation)
    }

    @TypeConverter
    fun toGeoLocation(geoLocation: String): Geolocation{
        return Gson().fromJson(geoLocation,Geolocation::class.java)
    }
}