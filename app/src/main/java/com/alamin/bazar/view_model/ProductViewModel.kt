package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.model.repository.ProductRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {


    val productList: LiveData<Response<List<Product>>>
        get() = productRepository.productList

    val productFromLocal: LiveData<List<Product>>
        get() = productRepository.productFromLocal


    fun getProductByIdList(ids: List<Int>): LiveData<List<Product>> = productRepository.getProductByIdList(ids)

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