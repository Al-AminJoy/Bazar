package com.alamin.bazar.view.adapter

import com.alamin.bazar.model.data.Checkout

interface CartClickListener {
    fun onClick(checkout: Checkout)
}