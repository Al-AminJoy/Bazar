package com.alamin.bazar.model.repository


import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.data.UserData
import com.alamin.bazar.model.data.UserResponse
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserRepository"
class UserRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val userFlow = MutableStateFlow<Response<User>>(Response.Empty())
    private val loginResponseFlow = MutableStateFlow<Response<UserResponse>>(Response.Empty())

    val getLoginResponse: StateFlow<Response<UserResponse>>
        get() = loginResponseFlow.asStateFlow()

    val user: StateFlow<Response<User>>
    get() = userFlow.asStateFlow()

    suspend fun requestUser(id: Int){
            userFlow.emit(Response.Loading())
            val response = apiInterface.getUser(id)
            if (response.isSuccessful){
                response.body()?.let {
                    userFlow.emit(Response.Success(it))
                }
            }else{
                userFlow.emit(Response.Error("Network Error"))
            }
    }

     suspend fun loginUser(userData: UserData){
            userFlow.emit(Response.Loading())
            val response = apiInterface.login(userData)
            if (response.isSuccessful){
                response.body()?.let {
                    loginResponseFlow.emit(Response.Success(it))            }
            }else{
                loginResponseFlow.emit(Response.Error("Network Error"))
            }
    }

     suspend fun signUpUser(user: User){
            userFlow.emit(Response.Loading())
            val response = apiInterface.signup(user)
            if (response.isSuccessful){
                response.body()?.let {
                    userFlow.emit(Response.Success(it))
                }
            }else{
                userFlow.emit(Response.Error("Network Error"))
            }


    }

     suspend fun updateUser(user: User){
        userFlow.emit(Response.Loading())
        //TODO: Should Use Dynamic Id
        val response = apiInterface.updateUser(1,user)
         if (response.isSuccessful){
             response.body()?.let {
                 userFlow.emit(Response.Success(it))
             }
         }else{
             userFlow.emit(Response.Error("Network Error"))
         }


    }

}