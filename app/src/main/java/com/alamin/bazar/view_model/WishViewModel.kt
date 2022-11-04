package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.repository.WishRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WishViewModel @Inject constructor(private val wishRepository: WishRepository): ViewModel() {

    val wishList: StateFlow<List<Wish>?> = wishRepository.wishList
        .stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(),
    null)

    fun insertWish(wish: Wish){
        viewModelScope.launch {
            withContext(IO){
                wishRepository.insertWish(wish)
            }
        }
    }

    fun getWishByProductId(productId: Int): StateFlow<Wish?> = wishRepository.getWishByProductId(productId)
        .stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(),
        null)


    fun deleteWish(productId: Int){
        viewModelScope.launch {
            withContext(IO){
                wishRepository.deleteWish(productId)
            }
        }
    }
}