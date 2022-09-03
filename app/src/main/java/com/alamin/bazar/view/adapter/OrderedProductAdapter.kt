package com.alamin.bazar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alamin.bazar.databinding.RowOrdersBinding
import com.alamin.bazar.model.data.Checkout
import com.alamin.bazar.model.data.Invoice
import javax.inject.Inject

class OrderedProductAdapter @Inject constructor() : RecyclerView.Adapter<OrderedProductAdapter.OrderedViewHolder>() {

    private var orderList = arrayListOf<Checkout>()

    inner class OrderedViewHolder(val binding: RowOrdersBinding): RecyclerView.ViewHolder(binding.root) {

        fun binding(checkout: Checkout){
            binding.checkout = checkout
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderedViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrderedViewHolder(RowOrdersBinding.inflate(layoutInflater,parent,false,null))
    }

    override fun onBindViewHolder(holder: OrderedViewHolder, position: Int) {
        holder.binding(orderList[position])
    }

    override fun getItemCount(): Int {
       return orderList.size
    }

    fun setData(newList: ArrayList<Checkout>){
        orderList = newList;
    }

}