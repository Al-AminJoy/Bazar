package com.alamin.bazar.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.data.UserData
import com.alamin.bazar.model.data.UserResponse
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.APIResponse
import javax.inject.Inject

private const val TAG = "UserRepository"
class UserRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val userLiveData = MutableLiveData<User>()
    private val loginResponseLiveData = MutableLiveData<UserResponse>()

    val getLoginResponse: LiveData<UserResponse>
        get() = loginResponseLiveData

    val user: LiveData<User>
    get() = userLiveData

    suspend fun requestUser(id: Int){
        val response = apiInterface.getUser(id);
        response.body()?.let {
            if (response.isSuccessful){
                userLiveData.postValue(response.body())
            }
        }
    }

    suspend fun loginUser(userData: UserData,apiResponse: APIResponse){
        val response = apiInterface.login(userData)
        if (response.isSuccessful){
            response.body()?.let {
                loginResponseLiveData.postValue(response.body())
                apiResponse.onSuccess("Success")
            }
        }else{
            apiResponse.onFailed("Failed")
        }
    }

    suspend fun signUpUser(user: User,apiResponse: APIResponse){
        val response = apiInterface.signup(user)
            if (response.isSuccessful){
                response.body()?.let {
                userLiveData.postValue(response.body())
                apiResponse.onSuccess("Success")
            }
        }else{
            apiResponse.onFailed("Failed")
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