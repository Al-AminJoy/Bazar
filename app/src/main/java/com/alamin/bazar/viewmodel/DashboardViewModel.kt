package com.alamin.bazar.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.ProductRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DashboardViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {


    val productList: StateFlow<Response<List<Product>>>
        get() = productRepository.productList

    val productFromLocal: StateFlow<List<Product>?>  = productRepository.productFromLocal.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(),
    null)




    fun requestProduct(){
        viewModelScope.launch {
            withContext(IO){
                productRepository.requestProduct()
            }
        }
    }
    fun insertProduct(products: List<Product>){
        viewModelScope.launch{
            withContext(IO){
                productRepository.insertProducts(products)
            }
        }
    }

}