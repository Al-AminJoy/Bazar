package com.alamin.bazar.view.adapter

import com.alamin.bazar.model.data.Product

interface ProductClickListener {
   fun onClick(product: Product)
}