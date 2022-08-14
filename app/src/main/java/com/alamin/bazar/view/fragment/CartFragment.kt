package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.databinding.FragmentCartBinding

import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory

import javax.inject.Inject

private const val TAG = "CartFragment"
class CartFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
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

        activity?.let {
            cartViewModel.getAllCart().observe(it, Observer { list ->
                val productIdList = list.map { cartProduct -> cartProduct.productId }.toList()
                Log.d(TAG, "onCreateView: $productIdList")
                if (productIdList.isNotEmpty()){
                        productViewModel.getProductByIdList(productIdList).observe(it, Observer {
                            Log.d(TAG, "onCreateView: ${it}")
                        })
                    }
            })
        }


        return binding.root
    }


}