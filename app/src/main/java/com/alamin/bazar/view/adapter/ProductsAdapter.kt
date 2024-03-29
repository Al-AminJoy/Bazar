package com.alamin.bazar.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alamin.bazar.databinding.RowProductBinding
import com.alamin.bazar.model.data.Product
import javax.inject.Inject

private const val TAG = "ProductsAdapter"
class ProductsAdapter @Inject constructor (private val productDiffUtils: ProductDiffUtils): RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var productList = arrayListOf<Product>()
    private lateinit var productClickListener: ProductClickListener
    private var dataList = arrayListOf<Product>()

    inner class ProductViewHolder(private val binding: RowProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun binding(product: Product){
            binding.product = product
            binding.productClick = productClickListener
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RowProductBinding.inflate(inflater,parent,false,null)
        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductsAdapter.ProductViewHolder, position: Int) {
        val productItem: Product = productList[position]
        holder.binding(productItem)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(newList : ArrayList<Product>){
        dataList = newList
        productDiffUtils.setProduct(productList,newList)
        val diffResult = DiffUtil.calculateDiff(productDiffUtils)
        productList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnClick(productClickListener: ProductClickListener){
        this.productClickListener = productClickListener
    }

    fun filteredData(text: String){
        var filteredList = arrayListOf<Product>()
        for (item in dataList){
            if (item.title.contains(text,ignoreCase = true)){
                filteredList.add(item)
            }else if (item.category.contains(text,ignoreCase = true)){
                filteredList.add(item)
            }
        }
        var diffResult: DiffUtil.DiffResult
        if (filteredList.isEmpty()){
            productDiffUtils.setProduct(dataList,productList)
            diffResult = DiffUtil.calculateDiff(productDiffUtils)
            productList = dataList
        }else{
            productDiffUtils.setProduct(productList,filteredList)
            diffResult = DiffUtil.calculateDiff(productDiffUtils)
            productList = filteredList
        }
        diffResult.dispatchUpdatesTo(this)


    }

}