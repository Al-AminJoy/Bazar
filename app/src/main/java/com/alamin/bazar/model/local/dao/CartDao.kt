package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alamin.bazar.model.data.CartProduct

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(carts: List<CartProduct>)

    @Query("SELECT * FROM cartproduct")
    fun getAllCart(): LiveData<CartProduct>

    @Delete
    fun deleteCart(cartProduct: CartProduct)
}