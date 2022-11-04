package com.alamin.bazar.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ProductRepository"

class ProductRepository @Inject constructor(
    private val apiInterface: APIInterface,
    private val localDatabase: LocalDatabase
) {
    private var flowProductList = MutableStateFlow<Response<List<Product>>>(Response.Empty())
    private val productDao = localDatabase.productDao()

    val productList: StateFlow<Response<List<Product>>>
        get() = flowProductList.asStateFlow()

    val productFromLocal: Flow<List<Product>>
        get() = productDao.getAllProduct()

    fun getProductByIdList(ids: List<Int>): Flow<List<Product>> = productDao.getProductByIdList(ids)

    suspend fun requestProduct() {
            flowProductList.emit(Response.Loading())
            val response = apiInterface.getProducts()
            if (response.isSuccessful) {
                response.body()?.let {
                    flowProductList.emit(Response.Success(it))
                }
            } else {
                flowProductList.emit(Response.Error("Error"))
            }

    }


    suspend fun insertProducts(products: List<Product>) {
        productDao.insertProduct(products)
    }
}