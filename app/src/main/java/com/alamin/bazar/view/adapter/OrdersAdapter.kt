package com.alamin.bazar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.bazar.databinding.RowOrderBinding
import com.alamin.bazar.model.data.Invoice
import javax.inject.Inject

class OrdersAdapter @Inject constructor(private val ordersDiffUtils: OrdersDiffUtils): RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {

    private var orderList = arrayListOf<Invoice>()
    private lateinit var orderClickListener: OrderClickListener

    inner class ViewHolder(private val binding: RowOrderBinding): RecyclerView.ViewHolder(binding.root){
        fun binding(invoice: Invoice){
            binding.invoice = invoice
            binding.onOrderClick = orderClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(RowOrderBinding.inflate(inflater,parent,false,null));
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        holder.binding(orderList[position])
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setData(newOrders : ArrayList<Invoice>){
        ordersDiffUtils.setOrderDiffUtils(orderList,newOrders)
        val diffResult = DiffUtil.calculateDiff(ordersDiffUtils)
        orderList = newOrders
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOrderClick(orderClickListener: OrderClickListener){
        this.orderClickListener = orderClickListener
    }

}