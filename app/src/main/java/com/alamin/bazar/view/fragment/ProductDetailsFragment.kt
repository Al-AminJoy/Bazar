package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentProductDetailsBinding
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import com.alamin.bazar.view_model.WishViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject


private const val TAG = "ProductDetailsFragment"
class ProductDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var cartViewModel: CartViewModel
    lateinit var wishViewModel: WishViewModel
    private lateinit var binding: FragmentProductDetailsBinding
    private  val arg by navArgs<ProductDetailsFragmentArgs>()
    private  var isWished: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        binding.product = arg.product

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectProductDetails(this)

        cartViewModel = ViewModelProvider(this,viewModelFactory)[CartViewModel::class.java]
        wishViewModel = ViewModelProvider(this,viewModelFactory)[WishViewModel::class.java]

        binding.setOnAddClick {
            cartViewModel.addProduct()
        }

        binding.setOnRemoveClick {
            cartViewModel.removeProduct()
        }

        binding.setOnAddCartClick {
            cartViewModel.requestAddCart(arg.product)
        }



        binding.setOnWishClick {
            if (isWished){
                wishViewModel.deleteWish(arg.product.id)
            }else{
                val wish = Wish(0,arg.product.id)
                wishViewModel.insertWish(wish)
            }
        }

        wishViewModel.getWishByProductId(arg.product.id).observe(requireActivity(), Observer {
            isWished = it != null
            binding.isWished = isWished
        })

        cartViewModel.count.observe(requireActivity(), Observer {
            binding.txtQuantity.text = it.toString()
        })

        cartViewModel.message.observe(requireActivity(), Observer {
            it?.let {
                Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        })

        cartViewModel.cartAddResponse.observe(requireActivity(), Observer {
            when(it){
                is Response.Loading ->{
                    findNavController().navigate(R.id.action_productDetailsFragment_to_loadingFragment)
                }
                is Response.Success ->{
                    it.data?.let {
                        findNavController().navigateUp()
                        Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                        cartViewModel.insertCart(it.products)
                    }
                }
                is Response.Error ->{
                    it.message?.let {
                        findNavController().navigateUp()
                        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        return binding.root
    }

}