package com.alamin.bazar.model.network

sealed class Response<T>(val data: T? = null,val message: String? = null){
    class Loading<T>(): Response<T>()
    class Empty<T>(): Response<T>()
    class Success<T>(data: T?): Response<T>(data = data)
    class Error<T>(message: String?): Response<T>(message = message)
}
