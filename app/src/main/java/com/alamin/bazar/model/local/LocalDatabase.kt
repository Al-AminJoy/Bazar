package com.alamin.bazar.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.local.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao;
}