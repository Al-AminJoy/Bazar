package com.alamin.bazar.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "UserViewModel"
class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val user = userRepository.user

     val inputName = MutableLiveData<String>()
     val inputContactNumber = MutableLiveData<String>()
     val inputCity = MutableLiveData<String>()
     val inputAddress = MutableLiveData<String>()

    fun requestUser(id: Int){
        viewModelScope.launch(IO) {
            userRepository.requestUser(id)
        }
    }

    fun updateUser(apiResponse: APIResponse){
        val name = inputName.value
        val contact = inputContactNumber.value
        val  city = inputCity.value
        val address = inputAddress.value

        Log.d(TAG, "updateUser: $name $contact $city $address")
    }

}