package com.alamin.bazar.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.bazar.model.data.Product
import javax.inject.Inject

class ProductDiffUtils @Inject constructor(): DiffUtil.Callback() {

    private var newList = arrayListOf<Product>()
    private var oldList = arrayListOf<Product>()

    fun setProduct(oldList: ArrayList<Product>, newList: ArrayList<Product>){
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }
}