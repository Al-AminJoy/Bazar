package com.alamin.bazar.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val PREFERENCE_NAME = "data_store"
class LocalDataStore (val context: Context){
    companion object PreferenceKeys{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
    }

    /**
     * User Id Operations
     */
    suspend fun storeId(id: Int){
        context.dataStore.edit {
            it[USER_ID] = id
        }
    }

    fun getId() = context.dataStore.data.map {
        it[USER_ID]?:-1
    }

    suspend fun removeId (){
        context.dataStore.edit {
            it.remove(USER_ID)
        }
    }

    /**
     * User Name Operation
     */

    suspend fun storeName(name: String){
        context.dataStore.edit {
            it[USER_NAME] = name
        }
    }

    fun getName() = context.dataStore.data.map {
        it[USER_NAME]?:-1
    }

    suspend fun removeName(){
        context.dataStore.edit {
            it.remove(USER_NAME)
        }
    }



}