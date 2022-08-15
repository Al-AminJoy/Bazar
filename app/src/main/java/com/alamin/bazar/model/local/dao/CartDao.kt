package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alamin.bazar.model.data.CartProduct

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(carts: List<CartProduct>)

    @Query("SELECT * FROM cartproduct")
    fun getAllCart(): LiveData<List<CartProduct>>

    @Delete
    fun deleteCart(cartProduct: CartProduct)

    @Query("DELETE FROM cartproduct WHERE productId=:id")
    suspend fun deleteCartById(id:Int)
}