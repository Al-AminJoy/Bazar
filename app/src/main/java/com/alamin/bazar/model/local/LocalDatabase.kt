package com.alamin.bazar.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alamin.bazar.model.data.*
import com.alamin.bazar.model.local.dao.*

@Database(entities = [User::class, CartProduct::class, Product::class, Invoice::class, Wish::class], version = 3, exportSchema = true)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao;
    abstract fun productDao(): ProductDao;
    abstract fun cartDao(): CartDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun wishDao(): WishDao
}