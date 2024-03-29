package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.CartRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CartViewModel @Inject constructor(private val cartRepository: CartRepository): ViewModel() {

    val cartAddResponse: StateFlow<Response<Cart>> = cartRepository.cartResponse

    var message = MutableSharedFlow<String>()

    val count = MutableStateFlow<Int>(0)

    fun getAllCart(): StateFlow<List<CartProduct>?> = cartRepository
        .getAllCart()
        .stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(),
        null)

    fun addProduct(){
        viewModelScope.launch {
                if (count.value >= 0){
                    count.value += 1
                }

        }
    }

    fun removeProduct(){
       viewModelScope.launch {
           if (count.value > 0){
               count.value -= 1
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
            viewModelScope.launch {
                    if (count.value!! <= 0){
                        message.emit("Please, Set Quantity")
                        return@launch
                    }
                    val dateString = SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis()))
                    val cartProduct = CartProduct(product.id, count.value!!)
                    val productList = arrayListOf<CartProduct>()
                    productList.add(cartProduct)
                    //TODO: User Id Should Dynamic
                    val cart = Cart(dateString,1,productList)
                withContext(IO){
                    cartRepository.requestAddCart(cart)
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