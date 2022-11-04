package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alamin.bazar.model.data.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<Product>)

    @Query("SELECT * FROM product WHERE id IN (:ids)")
    fun getProductByIdList(ids:List<Int>): Flow<List<Product>>

    @Query("SELECT * FROM product")
    fun getAllProduct(): LiveData<List<Product>>
}