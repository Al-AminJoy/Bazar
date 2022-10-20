package com.alamin.bazar.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.APIResponse
import javax.inject.Inject

private const val TAG = "UserRepository"
class UserRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val userLiveData = MutableLiveData<User>()

    val user: LiveData<User>
    get() = userLiveData

    suspend fun requestUser(id: Int){
        val response = apiInterface.getUser(id);
        Log.d(TAG, "requestUser: ${response.body()}")
        response.body()?.let {
            if (response.isSuccessful){
                userLiveData.postValue(response.body())
            }
        }
    }

    suspend fun updateUser(user: User,apiResponse: APIResponse){
        //TODO: Should Use Dynamic Id
        val response = apiInterface.updateUser(1,user)
        if (response.isSuccessful){
            response.body()?.let {
                userLiveData.postValue(response.body())
                apiResponse.onSuccess("Success")
            }
        }else{
            apiResponse.onFailed("Failed")
        }

    }

}