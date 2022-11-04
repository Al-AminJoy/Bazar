package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentDashBoardBinding
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.utils.Constants
import com.alamin.bazar.view.adapter.ProductClickListener
import com.alamin.bazar.view.adapter.ProductsAdapter
import com.alamin.bazar.view.dialog.LoadingFragment
import com.alamin.bazar.view.dialog.LoadingFragmentDirections
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import javax.inject.Inject

private const val TAG = "DashBoardFragment"
class DashBoardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var productsAdapter: ProductsAdapter

    private lateinit var productViewModel: ProductViewModel

    private lateinit var binding : FragmentDashBoardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashBoardBinding.inflate(layoutInflater)

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectDashBoard(this)
        productViewModel = ViewModelProvider(this,viewModelFactory)[ProductViewModel::class.java]
        productViewModel.productList.observe(requireActivity(), Observer {
            when(it){
                is Response.Loading -> {

                }
                is Response.Success -> {
                    it.data?.let {
                        findNavController().navigateUp()
                        productViewModel.insertProduct(it)
                    }
                }
                is Response.Error -> {
                    it.message?.let {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = productsAdapter
        }

        lifecycleScope.launchWhenCreated {
            context?.let {
                productViewModel.productFromLocal.collect {
                    it?.let {
                        with(productsAdapter){
                            setData(ArrayList(it))
                            setOnClick(object: ProductClickListener{
                                override fun onClick(product: Product) {
                                    val action = DashBoardFragmentDirections.actionDashBoardFragmentToProductDetailsFragment(product)
                                    findNavController().navigate(action)
                                }
                            })
                        }
                    }
                }
            }
        }


        binding.txtSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(text: Editable?) {
                productsAdapter.filteredData(text.toString())
            }

        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (Constants.isOnline(requireActivity())){
            findNavController().navigate(R.id.action_dashBoardFragment_to_loadingFragment)
            productViewModel.requestProduct()
        }else{
            Toast.makeText(requireContext(), getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show()
        }
    }

}