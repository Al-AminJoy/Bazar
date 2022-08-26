package com.alamin.bazar.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: APIInterface,private val localDatabase: LocalDatabase) {
    private val cartDao = localDatabase.cartDao()
    private val cartLiveData = MutableLiveData<Cart>()

    val cartResponse: LiveData<Cart>
    get() = cartLiveData

    fun getAllCart(): LiveData<List<CartProduct>> = cartDao.getAllCart()

    suspend fun requestAddCart(cart: Cart): Boolean{
        val response = apiInterface.addCart(cart)

        response.body()?.let {
            if (response.isSuccessful){
                cartLiveData.postValue(it)
                return  true
            }
        }
        return false
    }

    suspend fun insertCart(carts: List<CartProduct>){
        cartDao.insertCart(carts)
    }

    suspend fun deleteCartById(id: Int){
        cartDao.deleteCartById(id)
    }

    suspend fun deleteAllCart(){
        cartDao.deleteAllCart()
    }

}