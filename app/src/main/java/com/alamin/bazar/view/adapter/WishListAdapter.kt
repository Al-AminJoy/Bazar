package com.alamin.bazar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.bazar.databinding.RowWishlistBinding
import com.alamin.bazar.model.data.Product
import javax.inject.Inject

class WishListAdapter @Inject constructor(private val productDiffUtils: ProductDiffUtils): RecyclerView.Adapter<WishListAdapter.ViewHolder>() {

    private var wishList = arrayListOf<Product>()
    private lateinit var productClickListener: ProductClickListener

    inner class ViewHolder(val binding: RowWishlistBinding): RecyclerView.ViewHolder(binding.root){
        fun binding(product: Product){
            binding.product = product
            binding.onAddCartClick = productClickListener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(RowWishlistBinding.inflate(inflater,parent,false,null))
    }

    override fun onBindViewHolder(holder: WishListAdapter.ViewHolder, position: Int) {
        holder.binding(wishList[position])
    }

    override fun getItemCount(): Int {
        return wishList.size
    }

    fun setData(newList: ArrayList<Product>){
        productDiffUtils.setProduct(wishList,newList)
        val diffResult = DiffUtil.calculateDiff(productDiffUtils)
        wishList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnProductClick(productClickListener: ProductClickListener){
        this.productClickListener = productClickListener
    }

}