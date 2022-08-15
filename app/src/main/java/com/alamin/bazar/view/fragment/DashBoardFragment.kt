package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentDashBoardBinding
import com.alamin.bazar.model.data.Product
import com.alamin.bazar.view.adapter.ProductClickListener
import com.alamin.bazar.view.adapter.ProductsAdapter
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

        productViewModel.productFromLocal.observe(requireActivity(), Observer {
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(),2)
                adapter = productsAdapter
            }
            with(productsAdapter){
                setData(ArrayList(it))
                setOnClick(object: ProductClickListener{
                    override fun onClick(product: Product) {
                        val action = DashBoardFragmentDirections.actionDashBoardFragmentToProductDetailsFragment(product)
                        findNavController().navigate(action)
                    }
                })
            }
        })

        return binding.root
    }

}