package com.alamin.bazar.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.repository.UserRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    val user = userRepository.user

    fun requestUser(id: Int){
        viewModelScope.launch(IO) {
            userRepository.requestUser(id)
        }
    }

}