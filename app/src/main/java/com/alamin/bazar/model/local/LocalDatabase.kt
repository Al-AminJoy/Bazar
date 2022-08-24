package com.alamin.bazar.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.local.dao.CartDao
import com.alamin.bazar.model.local.dao.ProductDao
import com.alamin.bazar.model.local.dao.UserDao

@Database(entities = [User::class, CartProduct::class, Product::class], version = 1, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao;
    abstract fun productDao(): ProductDao;
    abstract fun cartDao(): CartDao
}