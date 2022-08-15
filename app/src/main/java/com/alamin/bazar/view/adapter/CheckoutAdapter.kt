package com.alamin.bazar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.bazar.databinding.RowCartBinding
import com.alamin.bazar.model.data.Checkout
import javax.inject.Inject

class CheckoutAdapter @Inject constructor(private val checkoutDiffUtils: CheckoutDiffUtils):
    RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder>() {

    private var checkoutList = arrayListOf<Checkout>()
    private lateinit var cartClickListener: CartClickListener

    inner class CheckoutViewHolder(val binding: RowCartBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(checkout: Checkout){
            binding.checkout = checkout
            binding.cartClick = cartClickListener
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutAdapter.CheckoutViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CheckoutViewHolder(RowCartBinding.inflate(inflater,parent,false,null))
    }



    override fun onBindViewHolder(holder: CheckoutAdapter.CheckoutViewHolder, position: Int) {
        val item = checkoutList[position]
        holder.binding(item)
    }

    override fun getItemCount(): Int {
        return checkoutList.size
    }

    fun setData(newList: ArrayList<Checkout>){
        checkoutDiffUtils.setCheckoutDiffUtils(checkoutList,newList)
        val diffResult = DiffUtil.calculateDiff(checkoutDiffUtils)
        checkoutList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setCartClick(cartClickListener: CartClickListener){
        this.cartClickListener = cartClickListener
    }
}