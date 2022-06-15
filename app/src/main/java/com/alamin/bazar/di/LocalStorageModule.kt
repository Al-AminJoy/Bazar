package com.alamin.bazar.di

import android.content.Context
import androidx.room.Room
import com.alamin.bazar.model.local.LocalDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalStorageModule {
    @Singleton
    @Provides
    fun provideLocalDatabase(context: Context): LocalDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "local_database")
            .build();
    }
}