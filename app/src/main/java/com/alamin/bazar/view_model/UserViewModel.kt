package com.alamin.bazar.view_model

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Address
import com.alamin.bazar.model.data.Geolocation
import com.alamin.bazar.model.data.Name
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserViewModel"
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val user = userRepository.user
    lateinit var userInfo:User

     val inputFirstName = MutableLiveData<String>()
     val inputLastName = MutableLiveData<String>()
     val inputContactNumber = MutableLiveData<String>()
     val inputCity = MutableLiveData<String>()
     val inputHolding = MutableLiveData<String>()
     val inputStreet = MutableLiveData<String>()
     val inputZipcode = MutableLiveData<String>()
     val inputUserId = MutableLiveData<String>()
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

    fun dummyLogin(){
        inputUserId.value = "johnd"
        inputPassword.value = "m38rmF$"
    }

    fun requestUser(id: Int){
        viewModelScope.launch(IO) {
            userRepository.requestUser(id)
        }
    }

    fun updateUser(apiResponse: APIResponse){
        val firstName = inputFirstName.value
        val lastName = inputLastName.value
        val contact = inputContactNumber.value
        val  city = inputCity.value
        val holding = inputHolding.value
        val road = inputStreet.value
        val post = inputZipcode.value

        if (checkEmpty(firstName,lastName,contact,city,holding,road,post,apiResponse)){
            val updatedUser = User(0, Address(city!!,
                Geolocation(userInfo.address.geolocation.lat,userInfo.address.geolocation.longi),
                holding?.toInt()!!,
                road!!,post!!),userInfo.email,Name(firstName!!,lastName!!),userInfo.password,contact!!,userInfo.username)
            viewModelScope.launch (IO){
                userRepository.updateUser(updatedUser,object :APIResponse{
                    override fun onSuccess(message: String) {
                        viewModelScope.launch(Main) {
                            apiResponse.onSuccess(message)
                        }
                    }

                    override fun onFailed(message: String) {
                        viewModelScope.launch(Main) {
                            apiResponse.onFailed(message)
                        }
                    }

                })
            }
        }

    }

    private fun checkEmpty(
        firstName: String?,
        lastName: String?,
        contact: String?,
        city: String?,
        holding: String?,
        road: String?,
        post: String?,
        apiResponse: APIResponse
    ): Boolean {
        if (firstName ==null || TextUtils.isEmpty(firstName)){
            apiResponse.onFailed("Enter First Name")
            return false
        }
        if (lastName ==null || TextUtils.isEmpty(lastName)){
            apiResponse.onFailed("Enter Last Name")
            return false
        }
        if (contact ==null || TextUtils.isEmpty(contact)){
            apiResponse.onFailed("Enter Contact Number")
            return false
        }
        if (city ==null || TextUtils.isEmpty(city)){
            apiResponse.onFailed("Enter City")
            return false
        }
        if (holding ==null || TextUtils.isEmpty(holding)){
            apiResponse.onFailed("Enter House")
            return false
        }
        if (road ==null || TextUtils.isEmpty(road)){
            apiResponse.onFailed("Enter Road")
            return false
        }
        if (post ==null || TextUtils.isEmpty(post)){
            apiResponse.onFailed("Enter Postal Code")
            return false
        }

        return true

    }

}