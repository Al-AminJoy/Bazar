package com.alamin.bazar.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentOrderDetailsBinding
import com.alamin.bazar.model.data.Invoice
import com.alamin.bazar.view.adapter.OrderedProductAdapter
import com.alamin.bazar.view.adapter.OrdersAdapter
import com.alamin.bazar.view_model.InvoiceViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import javax.inject.Inject
import kotlin.math.round

class OrderDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var orderedProductAdapter: OrderedProductAdapter

    private lateinit var invoiceViewModel: InvoiceViewModel

    private val args by navArgs<OrderDetailsFragmentArgs>()

   private lateinit var binding: FragmentOrderDetailsBinding
   private lateinit var invoice: Invoice

    private var subtotal = 0.00
    private var shipping = 50.00
    private var total = 0.00

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrderDetailsBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectOrderDetails(this)
        invoiceViewModel = ViewModelProvider(this,viewModelFactory)[InvoiceViewModel::class.java]

        invoice = args.invoice
        binding.invoice = invoice
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = orderedProductAdapter
        }

        with(orderedProductAdapter){
            this.setData(ArrayList(invoice.checkoutHolder.products))
        }

        subtotal =
            round(invoice.checkoutHolder.products.sumOf { checkout -> checkout.price * checkout.quantity } * 100.0) / 100.0
        total = round((subtotal + shipping) * 100.0) / 100.0
        binding.txtSubTotal.text = "\u09F3  $subtotal"
        binding.txtShipping.text = "\u09F3  $shipping"
        binding.txtTotal.text = "\u09F3  $total"

        binding.setOnCancelClick {
            invoice.status = "Canceled"
            invoiceViewModel.updateInvoice(invoice)
            binding.invoice = invoice
            Toast.makeText(activity, "Order Cancelled", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

}