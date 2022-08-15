package com.alamin.bazar.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alamin.bazar.model.data.Checkout
import javax.inject.Inject

class CheckoutDiffUtils @Inject constructor(): DiffUtil.Callback() {
    private var oldList = arrayListOf<Checkout>()
    private var newList = arrayListOf<Checkout>()

    fun setCheckoutDiffUtils(oldList : ArrayList<Checkout>,newList: ArrayList<Checkout>){
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
        return oldList[oldItemPosition].productId == newList[newItemPosition].productId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].productId == newList[newItemPosition].productId -> false
            oldList[oldItemPosition].title == newList[newItemPosition].title -> false
            oldList[oldItemPosition].quantity == newList[newItemPosition].quantity -> false
            oldList[oldItemPosition].category == newList[newItemPosition].category -> false
            oldList[oldItemPosition].image == newList[newItemPosition].image -> false
            oldList[oldItemPosition].price == newList[newItemPosition].price -> false
            else -> true
        }
    }
}