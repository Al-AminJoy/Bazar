package com.alamin.bazar.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.Response
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: APIInterface,private val localDatabase: LocalDatabase) {
    private val cartDao = localDatabase.cartDao()
    private val cartLiveData = MutableLiveData<Response<Cart>>()

    val cartResponse: LiveData<Response<Cart>>
    get() = cartLiveData

    fun getAllCart(): LiveData<List<CartProduct>> = cartDao.getAllCart()

    suspend fun requestAddCart(cart: Cart){
        cartLiveData.postValue(Response.Loading())
        val response = apiInterface.addCart(cart)
        if (response.isSuccessful){
        response.body()?.let {
                cartLiveData.postValue(Response.Success(response.body()))
            }
        }else{
            cartLiveData.postValue(Response.Error("Network Error"))
        }
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