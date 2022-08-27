package com.alamin.bazar.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alamin.bazar.model.data.*
import com.alamin.bazar.model.local.dao.CartDao
import com.alamin.bazar.model.local.dao.InvoiceDao
import com.alamin.bazar.model.local.dao.ProductDao
import com.alamin.bazar.model.local.dao.UserDao

@Database(entities = [User::class, CartProduct::class, Product::class, Invoice::class], version = 2, exportSchema = true)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao;
    abstract fun productDao(): ProductDao;
    abstract fun cartDao(): CartDao
    abstract fun invoiceDao(): InvoiceDao
}