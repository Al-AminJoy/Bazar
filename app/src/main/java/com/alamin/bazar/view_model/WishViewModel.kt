package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.repository.WishRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class WishViewModel @Inject constructor(private val wishRepository: WishRepository): ViewModel() {

    val wishList = wishRepository.wishList

    fun insertWish(wish: Wish){
        viewModelScope.launch(IO) {
            wishRepository.insertWish(wish)
        }
    }

    fun getWishByProductId(productId: Int): LiveData<Wish>{
        return wishRepository.getWishByProductId(productId)
    }

    fun deleteWish(productId: Int){
        viewModelScope.launch (IO) {
            wishRepository.deleteWish(productId)
        }
    }
}