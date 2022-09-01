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
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentOrdersBinding
import com.alamin.bazar.view.adapter.OrdersAdapter
import com.alamin.bazar.view_model.InvoiceViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import javax.inject.Inject

private const val TAG = "OrdersFragment"
class OrdersFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    private lateinit var invoiceViewModel: InvoiceViewModel
    private lateinit var binding: FragmentOrdersBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectOrders(this)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }

        invoiceViewModel = ViewModelProvider(this,viewModelFactory)[InvoiceViewModel::class.java]
        invoiceViewModel.invoiceList.observe(requireActivity(), Observer {
            Log.d(TAG, "onCreateView: $it")
            ordersAdapter.setData(ArrayList(it))
        })
        return binding.root
    }

}