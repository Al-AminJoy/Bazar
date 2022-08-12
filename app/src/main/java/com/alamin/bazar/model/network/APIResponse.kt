package com.alamin.bazar.model.network

interface APIResponse {
    fun onSuccess(message: String)
    fun onFailed(message: String)
}