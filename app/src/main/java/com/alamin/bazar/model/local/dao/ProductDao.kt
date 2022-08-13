package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alamin.bazar.model.data.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<Product>)

    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<Product>>
}