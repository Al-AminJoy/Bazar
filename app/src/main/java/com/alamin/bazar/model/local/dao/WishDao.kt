package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alamin.bazar.model.data.Wish

@Dao
interface WishDao {

    @Insert
   suspend fun insertWish(wish: Wish)

   @Query("SELECT * FROM wish WHERE productId=:productId")
   fun getWishByProductId(productId: Int): LiveData<Wish>

   @Query("SELECT * FROM wish")
   fun getAllWish(): LiveData<List<Wish>>

   @Query("DELETE FROM wish WHERE productId=:productId")
   suspend fun deleteWish(productId: Int)
}