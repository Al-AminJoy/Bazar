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
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentWishListBinding
import com.alamin.bazar.model.data.Cart
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.APIResponse
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
                val wishProductList = wishList.map { wish -> wish.productId }
                productViewModel.getProductByIdList(wishProductList).observe(fragmentActivity, Observer {
                    Log.d(TAG, "onCreateView: $it")
                    with(wishListAdapter){
                        setData(ArrayList(it))
                        setOnProductClick(object : ProductClickListener{
                            override fun onClick(product: Product) {
                                cartViewModel.requestAddCartFromWish(product, object: APIResponse {
                                    override fun onSuccess(message: String) {
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                                        wishViewModel.deleteWish(product.id)
                                    }

                                    override fun onFailed(message: String) {
                                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                                    }

                                })
                            }
                        })
                    }
                })
            })
        }

        cartViewModel.cartAddResponse.observe(requireActivity(), Observer {
            if (it != null){
                cartViewModel.insertCart(it.products)
            }
        })

        return binding.root
    }
}