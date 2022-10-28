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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "UserRepository"
class UserRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val userLiveData = MutableLiveData<Response<User>>()
    private val loginResponseLiveData = MutableLiveData<Response<UserResponse>>()

    val getLoginResponse: LiveData<Response<UserResponse>>
        get() = loginResponseLiveData

    val user: LiveData<Response<User>>
    get() = userLiveData

    fun requestUser(id: Int){
        userLiveData.postValue(Response.Loading())
        apiInterface.getUser(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccessful){
                    it.body()?.let {
                        userLiveData.postValue(Response.Success(it))
                    }
                }else{
                    userLiveData.postValue(Response.Error("Network Error"))
                }
            }

    }

     fun loginUser(userData: UserData){
        userLiveData.postValue(Response.Loading())
        apiInterface.login(userData)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccessful){
                    it.body()?.let {
                        loginResponseLiveData.postValue(Response.Success(it))            }
                }else{
                    loginResponseLiveData.postValue(Response.Error("Network Error"))
                }
            }

    }

     fun signUpUser(user: User){
        userLiveData.postValue(Response.Loading())
        apiInterface.signup(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccessful){
                    it.body()?.let {
                        userLiveData.postValue(Response.Success(it))
                    }
                }else{
                    userLiveData.postValue(Response.Error("Network Error"))
                }
            }


    }

     fun updateUser(user: User){
        userLiveData.postValue(Response.Loading())
        //TODO: Should Use Dynamic Id
        apiInterface.updateUser(1,user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isSuccessful){
                    it.body()?.let {
                        userLiveData.postValue(Response.Success(it))
                    }
                }else{
                    userLiveData.postValue(Response.Error("Network Error"))
                }
            }


    }

}