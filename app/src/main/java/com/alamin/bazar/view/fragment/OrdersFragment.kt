package com.alamin.bazar.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.databinding.FragmentOrdersBinding
import com.alamin.bazar.model.data.Invoice
import com.alamin.bazar.view.adapter.OrderClickListener
import com.alamin.bazar.view.adapter.OrdersAdapter
import com.alamin.bazar.viewmodel.InvoiceViewModel
import com.alamin.bazar.viewmodel.OrdersViewModel
import com.alamin.bazar.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

private const val TAG = "OrdersFragment"
class OrdersFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var ordersAdapter: OrdersAdapter

    private lateinit var viewModel: OrdersViewModel
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

        viewModel = ViewModelProvider(this,viewModelFactory)[OrdersViewModel::class.java]
        lifecycleScope.launchWhenCreated {
            viewModel.invoiceList.collectLatest {
                it?.let {
                    if (it.isEmpty()){
                        binding.txtNoOrderMessage.text = "No Order Found"
                    }else{
                        binding.txtNoOrderMessage.text = ""
                    }
                    with(ordersAdapter){
                        setData(ArrayList(it))
                        setOrderClick(object : OrderClickListener{
                            override fun onOrderClick(invoice: Invoice) {
                                val action = OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(invoice)
                                findNavController().navigate(action)
                            }

                        })
                    }
                }
            }
        }

        return binding.root
    }

}