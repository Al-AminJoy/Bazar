package com.alamin.bazar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.CartProduct
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.CartRepository
import com.alamin.bazar.model.repository.ProductRepository
import com.alamin.bazar.model.repository.WishRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ProductDetailsViewModel @Inject constructor(private val cartRepository: CartRepository,
                                                  private val wishRepository: WishRepository,
                                                  private val productRepository: ProductRepository): ViewModel() {

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

    fun getWishByProductId(productId: Int): StateFlow<Wish?> = wishRepository.getWishByProductId(productId)
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

    fun deleteWish(productId: Int){
        viewModelScope.launch {
            withContext(IO){
                wishRepository.deleteWish(productId)
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

    fun getProductByIdList(ids: List<Int>): StateFlow<List<Product>?> = productRepository
        .getProductByIdList(ids)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

    fun insertCart(carts: List<CartProduct>){
        viewModelScope.launch{
            withContext(IO){
                cartRepository.insertCart(carts)
            }
        }
    }

    fun requestAddCart(product: Product){
            viewModelScope.launch {
                    if (count.value <= 0){
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



}