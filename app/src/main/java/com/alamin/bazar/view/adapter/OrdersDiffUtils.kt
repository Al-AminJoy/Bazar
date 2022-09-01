package com.alamin.bazar.view.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.alamin.bazar.model.data.Invoice
import javax.inject.Inject

class OrdersDiffUtils @Inject constructor(): DiffUtil.Callback() {
    private var oldList = arrayListOf<Invoice>()
    private var newList = arrayListOf<Invoice>()

    fun setOrderDiffUtils(oldList: ArrayList<Invoice>, newList: ArrayList<Invoice>){
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
        return oldList[oldItemPosition].invoiceId == newList[newItemPosition].invoiceId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}