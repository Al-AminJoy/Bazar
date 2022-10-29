package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.repository.CartRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartRepository: CartRepository): ViewModel() {

    val cartAddResponse = cartRepository.cartResponse

    var message = MutableLiveData<String>()

    val count = MutableLiveData<Int>().apply {
        value = 0
    }

    fun getAllCart(): LiveData<List<CartProduct>> = cartRepository.getAllCart()

    fun addProduct(){
        count.value?.let {
            if (it>=0){
                count.value = it+1
            }
        }
    }

    fun removeProduct(){
        count.value?.let {
            if (it>0){
                count.value = it-1
            }
        }
    }



    fun insertCart(carts: List<CartProduct>){
        viewModelScope.launch{
            withContext(IO){
                cartRepository.insertCart(carts)
            }
        }
    }

    fun requestAddCart(product: Product){
        count.value?.let {
            if (count.value!! <= 0){
                message.value = "Please, Set Quantity"
                return
            }
            val dateString = SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis()))
            val cartProduct = CartProduct(product.id, count.value!!)
            val productList = arrayListOf<CartProduct>()
            productList.add(cartProduct)
            //TODO: User Id Should Dynamic
            val cart = Cart(dateString,1,productList)

            viewModelScope.launch {
                withContext(IO){
                    cartRepository.requestAddCart(cart)
                }
            }

        }
    }

    fun requestAddCartFromWish(product: Product){
            val dateString = SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis()))
            val cartProduct = CartProduct(product.id, 1)
            val productList = arrayListOf<CartProduct>()
            productList.add(cartProduct)
            //TODO: User Id Should Dynamic
            val cart = Cart(dateString,1,productList)

            viewModelScope.launch{
               withContext(IO){
                   cartRepository.requestAddCart(cart)
               }
            }

        }


    fun deleteCartById(id: Int){
        viewModelScope.launch  {
            withContext(IO){
                cartRepository.deleteCartById(id)
            }
        }
    }

    fun deleteAllCart(){
            viewModelScope.launch {
                withContext(IO){
                    cartRepository.deleteAllCart()
                }
            }
        }

}