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
        return when{
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].title != newList[newItemPosition].title -> false
            oldList[oldItemPosition].price != newList[newItemPosition].price -> false
            oldList[oldItemPosition].image != newList[newItemPosition].image -> false
            oldList[oldItemPosition].rating != newList[newItemPosition].rating -> false
            oldList[oldItemPosition].category != newList[newItemPosition].category -> false
            oldList[oldItemPosition].description != newList[newItemPosition].description -> false
            else -> true
        }
    }
}