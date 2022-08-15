package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.databinding.FragmentCartBinding
import com.alamin.bazar.model.data.Checkout
import com.alamin.bazar.view.adapter.CartAdapter
import com.alamin.bazar.view.adapter.CartClickListener
import com.alamin.bazar.view.adapter.CheckoutAdapter

import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory

import javax.inject.Inject

private const val TAG = "CartFragment"
class CartFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var checkoutAdapter: CheckoutAdapter
    private lateinit var cartViewModel: CartViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding : FragmentCartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectCart(this)

        cartViewModel = ViewModelProvider(this,viewModelFactory)[CartViewModel::class.java]
        productViewModel = ViewModelProvider(this,viewModelFactory)[ProductViewModel::class.java]

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = checkoutAdapter
        }

        activity?.let { fragmentActivity ->
            cartViewModel.getAllCart().observe(fragmentActivity, Observer { list ->
                val productIdList = list.map { cartProduct -> cartProduct.productId }.toList()
                Log.d(TAG, "onCreateView: $productIdList")
                if (productIdList.isNotEmpty()){
                    var checkoutList  = arrayListOf<Checkout>()
                        productViewModel.getProductByIdList(productIdList).observe(fragmentActivity, Observer {
                            Log.d(TAG, "onCreateView: Inner")
                            for (product in it){
                                for (cart in list){
                                    if (product.id == cart.productId){
                                        checkoutList.add(Checkout(product.id,cart.quantity,product.title,product.category,product.image,product.price))
                                    }
                                }
                            }
                            with(checkoutAdapter){
                                setData(checkoutList)
                                setCartClick(object : CartClickListener{
                                    override fun onClick(checkout: Checkout) {
                                        cartViewModel.deleteCartById(checkout.productId)
                                    }

                                })
                            }
                            Log.d(TAG, "onCreateView: $checkoutList")
                        })

                    }
            })
        }


        return binding.root
    }


}