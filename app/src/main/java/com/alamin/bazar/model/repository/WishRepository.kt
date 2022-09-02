package com.alamin.bazar.model.repository

import androidx.lifecycle.LiveData
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

class WishRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {
    private val wishDao = localDatabase.wishDao()

    val wishList = wishDao.getAllWish()

    suspend fun insertWish(wish: Wish){
        wishDao.insertWish(wish)
    }

    fun getWishByProductId(productId: Int):LiveData<Wish>{
        return wishDao.getWishByProductId(productId)
    }

    suspend fun deleteWish(productId: Int){
        wishDao.deleteWish(productId)
    }

}