package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentWishListBinding
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.view.adapter.ProductClickListener
import com.alamin.bazar.view.adapter.WishListAdapter
import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import com.alamin.bazar.view_model.WishViewModel
import javax.inject.Inject

private const val TAG = "WishListFragment"
class WishListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var wishListAdapter: WishListAdapter

    private lateinit var productViewModel: ProductViewModel
    private lateinit var wishViewModel: WishViewModel
    private lateinit var cartViewModel: CartViewModel

    private lateinit var binding: FragmentWishListBinding
    private  var product: Product? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishListBinding.inflate(layoutInflater)

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectWishList(this)

        productViewModel = ViewModelProvider(this,viewModelFactory)[ProductViewModel::class.java]
        wishViewModel = ViewModelProvider(this,viewModelFactory)[WishViewModel::class.java]
        cartViewModel = ViewModelProvider(this,viewModelFactory)[CartViewModel::class.java]

        binding.recyclerView.apply {
            layoutManager  = LinearLayoutManager(requireContext())
            adapter = wishListAdapter
        }

        activity?.let { fragmentActivity ->
            wishViewModel.wishList.observe(fragmentActivity, Observer { wishList ->
                if (wishList.isEmpty()){
                    binding.txtNoWishMessage.text = "No Wished Item Found"
                }else{
                    binding.txtNoWishMessage.text = ""
                }
                val wishProductList = wishList.map { wish -> wish.productId }
                productViewModel.getProductByIdList(wishProductList).observe(fragmentActivity, Observer {
                    Log.d(TAG, "onCreateView: $it")
                    with(wishListAdapter){
                        setData(ArrayList(it))
                        setOnProductClick(object : ProductClickListener{
                            override fun onClick(product: Product) {
                                cartViewModel.requestAddCartFromWish(product)
                                this@WishListFragment.product = product
                            }
                        })
                    }
                })
            })
        }

        cartViewModel.cartAddResponse.observe(requireActivity(), Observer {
            when(it){
                is Response.Loading -> {
                    findNavController().navigate(R.id.action_wishListFragment_to_loadingFragment)
                }
                is Response.Success -> {
                    it.data?.let {
                        findNavController().navigateUp()
                        Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                        cartViewModel.insertCart(it.products)
                        product?.let {
                            wishViewModel.deleteWish(product?.id!!)
                        }

                    }

                }
                is Response.Error -> {
                    it.message.let {
                        findNavController().navigateUp()
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        return binding.root
    }
}