package com.alamin.bazar.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: APIInterface,private val localDatabase: LocalDatabase) {
    private val cartDao = localDatabase.cartDao()
    private val cartFlow = MutableStateFlow<Response<Cart>>(Response.Empty())

    val cartResponse: StateFlow<Response<Cart>>
    get() = cartFlow.asStateFlow()

    fun getAllCart(): Flow<List<CartProduct>> = cartDao.getAllCart()

    suspend fun requestAddCart(cart: Cart){
        cartFlow.emit(Response.Loading())
        val response = apiInterface.addCart(cart)
        if (response.isSuccessful){
            response.body()?.let {
                cartFlow.emit(Response.Success(it))
            }
        }else{
            cartFlow.emit(Response.Error("Network Error"))
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