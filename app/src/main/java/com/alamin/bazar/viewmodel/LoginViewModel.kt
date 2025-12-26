package com.alamin.bazar.viewmodel


import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.data.UserData
import com.alamin.bazar.model.data.UserResponse
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.ProductRepository
import com.alamin.bazar.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val inputUserName = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    val loginResponse : StateFlow<Response<UserResponse>>
        get() = userRepository.getLoginResponse

    val user = userRepository.user
    val message = MutableSharedFlow<String>()

    fun dummyLogin(){
        inputUserName.value = "johnd"
        inputPassword.value = "m38rmF$"
    }

    fun requestUser(id: Int){
        viewModelScope.launch {
            withContext(IO){
                userRepository.requestUser(id)
            }
        }
    }

    fun loginUser(){
        val userName = inputUserName.value
        val userPassword = inputPassword.value
        viewModelScope.launch {
            if (userName.equals(null) || TextUtils.isEmpty(userName)){
                message.emit("Please, Enter Name")
            }else if (userPassword.equals(null) || TextUtils.isEmpty(userPassword)){
                message.emit("Please, Enter Password")
            }else{
                val userData = UserData(userName?.trim()!!,userPassword?.trim()!!)
                withContext(IO){
                    userRepository.loginUser(userData)
                }
            }
        }
    }

}