package com.alamin.bazar.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.data.UserData
import com.alamin.bazar.model.data.UserResponse
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

private const val TAG = "UserDataRepository"
class UserDataRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val loginResponseLiveData = MutableLiveData<UserResponse>()

    val getLoginResponse: LiveData<UserResponse>
    get() = loginResponseLiveData

     suspend fun loginUser(userData: UserData): Boolean{
        val response = apiInterface.login(userData)
         Log.d(TAG, "loginUser: ${response.code()} ${response.message()}")
        response.body()?.let {
            if (response.isSuccessful){
                    loginResponseLiveData.postValue(response.body())
                    return true
            }
        }
        return false
    }
}