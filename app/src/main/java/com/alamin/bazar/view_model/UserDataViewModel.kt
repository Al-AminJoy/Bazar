package com.alamin.bazar.view_model

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.UserData
import com.alamin.bazar.model.data.UserResponse
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.repository.UserDataRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserDataViewModel @Inject constructor(private val userDataRepository: UserDataRepository): ViewModel() {
    val inputName = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    val loginResponse : LiveData<UserResponse>
    get() = userDataRepository.getLoginResponse

    fun loginUser(apiResponse: APIResponse){
       /* val userName = inputName.value
        val userPassword = inputPassword.value   */
        val userName = "johnd"
        val userPassword = "m38rmF$"
        if (userName.equals(null) || TextUtils.isEmpty(userName)){
            apiResponse.onFailed("Please, Enter Name")
        }else if (userPassword.equals(null) || TextUtils.isEmpty(userPassword)){
            apiResponse.onFailed("Please, Enter Password")
        }else{
            val userData = UserData(userName?.trim()!!,userPassword?.trim()!!)

            viewModelScope.launch(IO) {
              val response =  userDataRepository.loginUser(userData)
                if (response){
                    withContext(Main){
                        apiResponse.onSuccess("Success")
                    }
                }else{
                    withContext(Main){
                        apiResponse.onFailed("Failed")
                    }
                }
            }
        }
    }



    fun signUpUser(apiResponse: APIResponse){
        val userName = inputName.value
        val userPassword = inputPassword.value
        if (userName.equals(null) || TextUtils.isEmpty(userName)){
            apiResponse.onFailed("Please, Enter Name")
        }else if (userPassword.equals(null) || TextUtils.isEmpty(userPassword)){
            apiResponse.onFailed("Please, Enter Password")
        }else{
            val userData = UserData(userName?.trim()!!,userPassword?.trim()!!)

            viewModelScope.launch(IO) {
                val response =  userDataRepository.signUpUser(userData)
                if (response){
                    withContext(Main){
                        apiResponse.onSuccess("Success")
                    }
                }else{
                    withContext(Main){
                        apiResponse.onFailed("Failed")
                    }
                }
            }
        }
    }
}