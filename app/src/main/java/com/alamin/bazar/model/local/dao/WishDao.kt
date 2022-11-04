package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alamin.bazar.model.data.Wish
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {

    @Insert
   suspend fun insertWish(wish: Wish)

   @Query("SELECT * FROM wish WHERE productId=:productId")
   fun getWishByProductId(productId: Int): Flow<Wish>

   @Query("SELECT * FROM wish")
   fun getAllWish(): Flow<List<Wish>>

   @Query("DELETE FROM wish WHERE productId=:productId")
   suspend fun deleteWish(productId: Int)
}