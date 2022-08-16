package com.alamin.bazar.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

const val PREFERENCE_NAME = "data_store"
class LocalDataStore @Inject constructor (val context: Context){
    companion object PreferenceKeys{
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)
        val USER = stringPreferencesKey("user")
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_TOKEN = stringPreferencesKey("user_token")
        val LAST_ADDRESS = stringPreferencesKey("last_address")
    }

    /**
     * User Operation
     */
    suspend fun storeUser(user: String){
        context.dataStore.edit {
            it[USER] = user
        }
    }

    fun getUser() = context.dataStore.data.map {
        it[USER]?:-1
    }


    suspend fun removeUser(){
        context.dataStore.edit {
            it.remove(USER)
        }
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

    /**
     * User Token Operation
     */

    suspend fun storeToken(token: String){
        context.dataStore.edit {
            it[USER_TOKEN] = token
        }
    }

    fun getToken() = context.dataStore.data.map {
        it[USER_TOKEN]?:-1
    }

    suspend fun removeToken(){
        context.dataStore.edit {
            it.remove(USER_TOKEN)
        }
    }

    /**
     * Address Operation
     */

    suspend fun storeLastAddress(address: String){
        context.dataStore.edit {
            it[LAST_ADDRESS] = address
        }
    }

    fun getLastAddress() = context.dataStore.data.map {
        it[LAST_ADDRESS]?:-1
    }

    suspend fun removeLastAddress(){
        context.dataStore.edit {
            it.remove(LAST_ADDRESS)
        }
    }

}