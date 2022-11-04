package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.ProductRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {


    val productList: LiveData<Response<List<Product>>>
        get() = productRepository.productList

    val productFromLocal: StateFlow<List<Product>?>  = productRepository.productFromLocal.stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(),
    null)


    fun getProductByIdList(ids: List<Int>): StateFlow<List<Product>?> = productRepository
        .getProductByIdList(ids)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            null
        )

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