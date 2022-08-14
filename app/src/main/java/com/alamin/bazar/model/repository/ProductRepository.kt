package com.alamin.bazar.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

private const val TAG = "ProductRepository"
class ProductRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {
    private var liveProductList = MutableLiveData<List<Product>>()
    private val productDao = localDatabase.productDao()

    val productList: LiveData<List<Product>>
    get() = liveProductList

    val productFromLocal: LiveData<List<Product>>
    get() = productDao.getAllProduct()

    fun getProductByIdList(ids:List<Int>):LiveData<List<Product>> = productDao.getProductByIdList(ids)

    suspend fun requestProduct(){
        var response = apiInterface.getProducts()
        response.body()?.let {
            if (response.isSuccessful){
                liveProductList.postValue(response.body())
            }
        }
    }

    suspend fun insertProducts(products: List<Product>){
        productDao.insertProduct(products)
    }
}