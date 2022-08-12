package com.alamin.bazar.view_model

import androidx.lifecycle.ViewModel
import com.alamin.bazar.model.repository.UserDataRepository
import javax.inject.Inject

class UserDataViewModel @Inject constructor(userDataRepository: UserDataRepository): ViewModel() {
}