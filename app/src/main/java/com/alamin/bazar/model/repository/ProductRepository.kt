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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "ProductRepository"
class ProductRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {
    private var liveProductList = MutableLiveData<Response<List<Product>>>()
    private val productDao = localDatabase.productDao()

    val productList: LiveData<Response<List<Product>>>
    get() = liveProductList

    val productFromLocal: LiveData<List<Product>>
    get() = productDao.getAllProduct()

    fun getProductByIdList(ids:List<Int>):Flow<List<Product>> = productDao.getProductByIdList(ids)

     fun requestProduct(){
         liveProductList.postValue(Response.Loading())
        apiInterface.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                if (it.isSuccessful){
                    it.body()?.let {
                        liveProductList.postValue(Response.Success(it))
                    }
                }else{
                    liveProductList.postValue(Response.Error("Error"))
                }
            }

     }


    suspend fun insertProducts(products: List<Product>){
        productDao.insertProduct(products)
    }
}