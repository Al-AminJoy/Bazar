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
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentProductDetailsBinding
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.network.APIResponse
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
            binding.btnCart.visibility = View.GONE
            cartViewModel.requestAddCart(arg.product, object: APIResponse{
                override fun onSuccess(message: String) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    binding.btnCart.visibility = View.VISIBLE
                }

                override fun onFailed(message: String) {
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    binding.btnCart.visibility = View.VISIBLE
                }

            })
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

        cartViewModel.cartAddResponse.observe(requireActivity(), Observer {
            cartViewModel.insertCart(it.products)
        })

        return binding.root
    }

}