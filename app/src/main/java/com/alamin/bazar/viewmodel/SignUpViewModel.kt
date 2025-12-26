package com.alamin.bazar.viewmodel


import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Address
import com.alamin.bazar.model.data.Geolocation
import com.alamin.bazar.model.data.Name
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.data.User
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

class SignUpViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {
    val inputPassword = MutableLiveData<String>()
    val inputUserName = MutableLiveData<String>()
    val inputFirstName = MutableLiveData<String>()
    val inputLastName = MutableLiveData<String>()
    val inputContactNumber = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputCity = MutableLiveData<String>()
    val inputHolding = MutableLiveData<String>()
    val inputStreet = MutableLiveData<String>()
    val inputZipcode = MutableLiveData<String>()

    val user = userRepository.user
    val message = MutableSharedFlow<String>()

    fun signUpUser(){
        val firstName = inputFirstName.value
        val lastName = inputLastName.value
        val contact = inputContactNumber.value
        val email = inputEmail.value
        val password = inputPassword.value
        val  city = inputCity.value
        val holding = inputHolding.value
        val road = inputStreet.value
        val post = inputZipcode.value
        viewModelScope.launch {
            if (checkEmpty(firstName,lastName,contact,email,password,city,holding,road,post)){

                val createUser = User(0, Address(city!!,
                    Geolocation("0.0","0.0"),
                    holding?.toInt()!!,
                    road!!,post!!),email!!,Name(firstName!!,lastName!!),password!!,contact!!,"")


                withContext(IO){
                    userRepository.signUpUser(createUser)
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