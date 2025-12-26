package com.alamin.bazar.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.*
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "UserViewModel"
class EditProfileViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {


    val user = userRepository.user

    val message = MutableSharedFlow<String>()

    lateinit var userInfo:User

     val inputUserName = MutableLiveData<String>()
     val inputFirstName = MutableLiveData<String>()
     val inputLastName = MutableLiveData<String>()
     val inputContactNumber = MutableLiveData<String>()
     val inputEmail = MutableLiveData<String>()
     val inputCity = MutableLiveData<String>()
     val inputHolding = MutableLiveData<String>()
     val inputStreet = MutableLiveData<String>()
     val inputZipcode = MutableLiveData<String>()
     val inputPassword = MutableLiveData<String>()

    fun setUserData(user:User){
        userInfo = user
        inputFirstName.value = user.name.firstname
        inputLastName.value = user.name.lastname
        inputContactNumber.value = user.phone
        inputCity.value = user.address.city
        inputHolding.value = user.address.number.toString()
        inputStreet.value = user.address.street
        inputZipcode.value = user.address.zipcode
    }




    fun updateUser(){
        val firstName = inputFirstName.value
        val lastName = inputLastName.value
        val contact = inputContactNumber.value
        val  city = inputCity.value
        val holding = inputHolding.value
        val road = inputStreet.value
        val post = inputZipcode.value


            viewModelScope.launch {
                if (checkEmpty(firstName,lastName,contact,"email@email.com","no_password",city,holding,road,post)){
                    val updatedUser = User(0, Address(city!!,
                        Geolocation(userInfo.address.geolocation.lat,userInfo.address.geolocation.longi),
                        holding?.toInt()!!,
                        road!!,post!!),userInfo.email,Name(firstName!!,lastName!!),userInfo.password,contact!!,userInfo.username)
                withContext(IO){
                    userRepository.updateUser(updatedUser)
                }
            }
        }

    }

    private suspend fun checkEmpty(
        firstName: String?,
        lastName: String?,
        contact: String?,
        email: String?,
        password: String?,
        city: String?,
        holding: String?,
        road: String?,
        post: String?): Boolean {
        if (firstName ==null || TextUtils.isEmpty(firstName)){
            message.emit("Enter First Name")
            return false
        }
        if (lastName ==null || TextUtils.isEmpty(lastName)){
            message.emit("Enter Last Name")
            return false
        }
        if (contact ==null || TextUtils.isEmpty(contact)){
            message.emit("Enter Contact Number")
            return false
        }
        if (city ==null || TextUtils.isEmpty(city)){
            message.emit("Enter City")
            return false
        }
        if (holding ==null || TextUtils.isEmpty(holding)){
            message.emit("Enter House")
            return false
        }
        if (road ==null || TextUtils.isEmpty(road)){
            message.emit("Enter Road")
            return false
        }
        if (post ==null || TextUtils.isEmpty(post)){
            message.emit("Enter Postal Code")
            return false
        }
        if (email ==null || TextUtils.isEmpty(email)){
            message.emit("Enter Email")
            return false
        }

        if (!email.isValid()){
            message.emit("Invalid Email")
            return false
        }

        if (password ==null || TextUtils.isEmpty(password)){
            message.emit("Enter Password")
            return false
        }

        return true

    }

    private fun String.isValid() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

}