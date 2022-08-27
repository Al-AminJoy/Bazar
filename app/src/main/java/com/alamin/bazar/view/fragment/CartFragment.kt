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
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentCartBinding
import com.alamin.bazar.model.data.*
import com.alamin.bazar.utils.LocalDataStore
import com.alamin.bazar.view.adapter.CartAdapter
import com.alamin.bazar.view.adapter.CartClickListener
import com.alamin.bazar.view.adapter.CheckoutAdapter

import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import java.util.*

import javax.inject.Inject
import kotlin.math.round

private const val TAG = "CartFragment"

class CartFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var localDataStore: LocalDataStore

    @Inject
    lateinit var checkoutAdapter: CheckoutAdapter
    private lateinit var cartViewModel: CartViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentCartBinding

    private var finalCheckoutList = arrayListOf<Checkout>()

    private var subtotal = 0.00
    private var shipping = 50.00
    private var total = 0.00


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentCartBinding.inflate(layoutInflater)

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectCart(this)




        cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
        productViewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]

        val holder = CheckoutHolder(finalCheckoutList)
        binding.setOnCheckoutClick {
            if (finalCheckoutList.isNotEmpty()) {
                val invoice = Invoice(
                    0,
                    "",
                    subtotal,
                    shipping,
                    total,
                    "",
                    false,
                    "",
                    "",
                    false,
                    holder
                )
                val action =
                    CartFragmentDirections.actionCartFragmentToCheckoutFragment(invoice)
                findNavController().navigate(action)


            } else {
                Toast.makeText(activity, "Add Product in Cart First", Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = checkoutAdapter
        }

        activity?.let { fragmentActivity ->
            cartViewModel.getAllCart().observe(fragmentActivity, Observer { list ->
                val productIdList = list.map { cartProduct -> cartProduct.productId }.toList()
                var checkoutList = arrayListOf<Checkout>()
                productViewModel.getProductByIdList(productIdList)
                    .observe(fragmentActivity, Observer {
                        for (product in it) {
                            for (cart in list) {
                                if (product.id == cart.productId) {
                                    checkoutList.add(
                                        Checkout(
                                            product.id,
                                            cart.quantity,
                                            product.title,
                                            product.category,
                                            product.image,
                                            product.price
                                        )
                                    )
                                }
                            }
                        }
                        with(checkoutAdapter) {
                            setCartClick(object : CartClickListener {
                                override fun onClick(checkout: Checkout) {
                                    cartViewModel.deleteCartById(checkout.productId)
                                }
                            })
                            finalCheckoutList = checkoutList
                            setData(checkoutList)
                        }
                        subtotal =
                            round(checkoutList.sumOf { checkout -> checkout.price * checkout.quantity } * 100.0) / 100.0
                        total = round((subtotal + shipping) * 100.0) / 100.0
                        binding.txtSubTotal.text = "\u09F3  $subtotal"
                        binding.txtShipping.text = "\u09F3  $shipping"
                        binding.txtTotal.text = "\u09F3  $total"
                    })

            })
        }


        return binding.root
    }

}