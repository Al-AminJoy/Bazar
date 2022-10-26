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
import com.alamin.bazar.model.network.Response
import javax.inject.Inject

private const val TAG = "UserRepository"
class UserRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val userLiveData = MutableLiveData<Response<User>>()
    private val loginResponseLiveData = MutableLiveData<Response<UserResponse>>()

    val getLoginResponse: LiveData<Response<UserResponse>>
        get() = loginResponseLiveData

    val user: LiveData<Response<User>>
    get() = userLiveData

    suspend fun requestUser(id: Int){
        userLiveData.postValue(Response.Loading())
        val response = apiInterface.getUser(id);
        if (response.isSuccessful){
            response.body()?.let {
                userLiveData.postValue(Response.Success(response.body()))
            }
        }else{
            userLiveData.postValue(Response.Error("Network Error"))
        }
    }

    suspend fun loginUser(userData: UserData){
        userLiveData.postValue(Response.Loading())
        val response = apiInterface.login(userData)
        if (response.isSuccessful){
            response.body()?.let {
                loginResponseLiveData.postValue(Response.Success(response.body()))            }
        }else{
            loginResponseLiveData.postValue(Response.Error("Network Error"))
        }
    }

    suspend fun signUpUser(user: User){
        userLiveData.postValue(Response.Loading())
        val response = apiInterface.signup(user)
            if (response.isSuccessful){
                response.body()?.let {
                    userLiveData.postValue(Response.Success(response.body()))
            }
        }else{
                userLiveData.postValue(Response.Error("Network Error"))
            }

    }

    suspend fun updateUser(user: User){
        userLiveData.postValue(Response.Loading())
        //TODO: Should Use Dynamic Id
        val response = apiInterface.updateUser(1,user)
        if (response.isSuccessful){
            response.body()?.let {
                userLiveData.postValue(Response.Success(response.body()))
            }
        }else{
            userLiveData.postValue(Response.Error("Network Error"))
        }

    }

}