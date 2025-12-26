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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class WishViewModel @Inject constructor(private val wishRepository: WishRepository,
                                        private val cartRepository: CartRepository,
                                        private val productRepository: ProductRepository): ViewModel() {

    var message = MutableSharedFlow<String>()

    val cartAddResponse: StateFlow<Response<Cart>> = cartRepository.cartResponse

    val wishList: StateFlow<List<Wish>?> = wishRepository.wishList
        .stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(),
    null)



    fun insertCart(carts: List<CartProduct>){
        viewModelScope.launch{
            withContext(IO){
                cartRepository.insertCart(carts)
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

    fun getProductByIdList(ids: List<Int>): StateFlow<List<Product>?> = productRepository
        .getProductByIdList(ids)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

    fun deleteWish(productId: Int){
        viewModelScope.launch {
            withContext(IO){
                wishRepository.deleteWish(productId)
            }
        }
    }
}