package com.alamin.bazar.view_model

import androidx.lifecycle.ViewModel
import com.alamin.bazar.model.repository.UserRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

}