package com.alamin.bazar.view_model

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.*
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "UserViewModel"
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val loginResponse : LiveData<UserResponse>
        get() = userRepository.getLoginResponse

    val user = userRepository.user
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

    fun dummyLogin(){
        inputUserName.value = "johnd"
        inputPassword.value = "m38rmF$"
    }

    fun requestUser(id: Int){
        viewModelScope.launch(IO) {
            userRepository.requestUser(id)
        }
    }

    fun loginUser(apiResponse: APIResponse){

         val userName = inputUserName.value
         val userPassword = inputPassword.value

        if (userName.equals(null) || TextUtils.isEmpty(userName)){
            apiResponse.onFailed("Please, Enter Name")
        }else if (userPassword.equals(null) || TextUtils.isEmpty(userPassword)){
            apiResponse.onFailed("Please, Enter Password")
        }else{
            val userData = UserData(userName?.trim()!!,userPassword?.trim()!!)
            viewModelScope.launch(IO) {
                userRepository.loginUser(userData,object :APIResponse{
                    override fun onSuccess(message: String) {
                        viewModelScope.launch(Main){
                            apiResponse.onSuccess(message)
                        }
                    }

                    override fun onFailed(message: String) {
                        viewModelScope.launch(Main){
                            apiResponse.onFailed(message)
                        }
                    }

                })
            }
        }
    }

    fun signUpUser(apiResponse: APIResponse){
        val firstName = inputFirstName.value
        val lastName = inputLastName.value
        val contact = inputContactNumber.value
        val email = inputEmail.value
        val password = inputPassword.value
        val  city = inputCity.value
        val holding = inputHolding.value
        val road = inputStreet.value
        val post = inputZipcode.value

        if (checkEmpty(firstName,lastName,contact,email,password,city,holding,road,post,apiResponse)){

            val createUser = User(0, Address(city!!,
                Geolocation("0.0","0.0"),
                holding?.toInt()!!,
                road!!,post!!),email!!,Name(firstName!!,lastName!!),password!!,contact!!,"")

            viewModelScope.launch(IO) {
                userRepository.signUpUser(createUser,object : APIResponse{
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

    fun updateUser(apiResponse: APIResponse){
        val firstName = inputFirstName.value
        val lastName = inputLastName.value
        val contact = inputContactNumber.value
        val  city = inputCity.value
        val holding = inputHolding.value
        val road = inputStreet.value
        val post = inputZipcode.value

        if (checkEmpty(firstName,lastName,contact,"no","no",city,holding,road,post,apiResponse)){
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
        email: String?,
        password: String?,
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
        if (email ==null || TextUtils.isEmpty(email)){
            apiResponse.onFailed("Enter Email")
            return false
        }

        if (!email.isValid()){
            apiResponse.onFailed("Invalid Email")
        }

        if (password ==null || TextUtils.isEmpty(password)){
            apiResponse.onFailed("Enter Password")
            return false
        }

        return true

    }

    private fun String.isValid() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

}