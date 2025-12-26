package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentProductDetailsBinding
import com.alamin.bazar.model.data.Wish
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.viewmodel.CartViewModel
import com.alamin.bazar.viewmodel.ProductDetailsViewModel
import com.alamin.bazar.viewmodel.ViewModelFactory
import com.alamin.bazar.viewmodel.WishViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


private const val TAG = "ProductDetailsFragment"
class ProductDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProductDetailsViewModel
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

        viewModel = ViewModelProvider(this,viewModelFactory)[ProductDetailsViewModel::class.java]

        binding.setOnAddClick {
            viewModel.addProduct()
        }

        binding.setOnRemoveClick {
            viewModel.removeProduct()
        }

        binding.setOnAddCartClick {
            viewModel.requestAddCart(arg.product)
        }



        binding.setOnWishClick {
            if (isWished){
                viewModel.deleteWish(arg.product.id)
            }else{
                val wish = Wish(0,arg.product.id)
                viewModel.insertWish(wish)
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getWishByProductId(arg.product.id).collectLatest {
                isWished = it != null
                binding.isWished = isWished
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.count.collectLatest {
                binding.txtQuantity.text = it.toString()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.message.collect{
                    Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.cartAddResponse.collectLatest {
                when(it){
                    is Response.Loading ->{
                        findNavController().navigate(R.id.action_productDetailsFragment_to_loadingFragment)
                    }
                    is Response.Success ->{
                        it.data?.let {
                            findNavController().navigateUp()
                            Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                            viewModel.insertCart(it.products)
                        }
                    }
                    is Response.Error ->{
                        it.message?.let {
                            findNavController().navigateUp()
                            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }

}