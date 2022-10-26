package com.alamin.bazar.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.utils.Constants
import javax.inject.Inject

private const val TAG = "ProductRepository"
class ProductRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {
    private var liveProductList = MutableLiveData<Response<List<Product>>>()
    private val productDao = localDatabase.productDao()

    val productList: LiveData<Response<List<Product>>>
    get() = liveProductList

    val productFromLocal: LiveData<List<Product>>
    get() = productDao.getAllProduct()

    fun getProductByIdList(ids:List<Int>):LiveData<List<Product>> = productDao.getProductByIdList(ids)

    suspend fun requestProduct(){
        liveProductList.postValue(Response.Loading())
        var response = apiInterface.getProducts()
        if (response.isSuccessful){
            response.body()?.let {
                liveProductList.postValue(Response.Success(response.body()))
            }
        }else{
            liveProductList.postValue(Response.Error("Error"))
        }

    }

    suspend fun insertProducts(products: List<Product>){
        productDao.insertProduct(products)
    }
}