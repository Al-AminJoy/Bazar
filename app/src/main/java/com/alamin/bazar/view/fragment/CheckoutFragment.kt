package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentCheckoutBinding
import com.alamin.bazar.model.data.Address
import com.alamin.bazar.model.data.Invoice
import com.alamin.bazar.model.data.User
import com.alamin.bazar.utils.LocalDataStore
import com.alamin.bazar.view_model.CartViewModel
import com.alamin.bazar.view_model.InvoiceViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "CheckoutFragment"

class CheckoutFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var localDataStore: LocalDataStore

    private lateinit var binding: FragmentCheckoutBinding

    private lateinit var cartViewModel: CartViewModel
    private lateinit var invoiceViewModel: InvoiceViewModel

    private val arg by navArgs<CheckoutFragmentArgs>()

    private var isCashOnDelivery = true
    private var isUserAddress = true
    private lateinit var userAddress: String
    private lateinit var customAddress: String
    private var deliveryAddress: String = ""
    private lateinit var address: Address
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(layoutInflater)

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectCheckout(this)
        cartViewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
        invoiceViewModel = ViewModelProvider(this, viewModelFactory)[InvoiceViewModel::class.java]



        lifecycleScope.launchWhenCreated {
            Log.d(TAG, "onCreateView: Called")
            localDataStore.getUser().collect { userInfo ->
                if (userInfo.trim().isNotEmpty()) {
                    user = Gson().fromJson(userInfo, User::class.java)
                    address = user.address
                    userAddress =
                        "${user.name.firstname} ${user.name.lastname}, ${address.number}, ${address.street}, ${address.city}-${address.zipcode}, Phone: ${user.phone}"
                    binding.userAddress = userAddress

                    lifecycleScope.launchWhenCreated {
                        localDataStore.getLastAddress().collect {
                            customAddress =
                                "${user.name.firstname} ${user.name.lastname}, $it, Phone: ${user.phone}"
                            binding.customAddress =
                                if (customAddress.trim()
                                        .isNotEmpty()
                                ) customAddress else " Address Not Set"
                        }
                    }
                }
            }
        }

        binding.setOnUserAddressClick {
            deliveryAddress = userAddress
            isUserAddress = true
        }

        binding.setOnCustomAddressClick {
            deliveryAddress = customAddress
            isUserAddress = false
        }

        binding.setOnEditCustomAddressClick {
            findNavController().navigate(R.id.action_checkoutFragment_to_customAddressFragment)
        }

        binding.setOnConfirmClick {
            val dateString = SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis()))
            if (!binding.btnCashOnDelivery.isChecked && !binding.btnOnlinePayment.isChecked) {
                Toast.makeText(activity, "Please, Select Delivery Method", Toast.LENGTH_SHORT)
                    .show()
            } else if (!binding.btnUserAddress.isChecked && !binding.btnCustomAddress.isChecked) {
                Toast.makeText(activity, "Please, Select Address", Toast.LENGTH_SHORT).show()
            } else if (deliveryAddress.trim().isEmpty() && binding.btnCustomAddress.isChecked) {
                Toast.makeText(activity, "Please, Insert Address First", Toast.LENGTH_SHORT).show()
            } else {
                isCashOnDelivery = binding.btnCashOnDelivery.isChecked
                val note = binding.txtNote.text.toString().ifEmpty { "" }
                val invoice = Invoice(
                    0,
                    dateString,
                    arg.invoice.subTotal,
                    arg.invoice.shippingCharge,
                    arg.invoice.total,
                    deliveryAddress,
                    isCashOnDelivery,
                    note,
                    "Pending",
                    false,
                    arg.invoice.checkoutHolder
                )
                cartViewModel.deleteAllCart()
                invoiceViewModel.insertInvoice(invoice)
                Toast.makeText(activity, "Order Confirmed", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_checkoutFragment_to_dashBoardFragment)
            }

        }


        return binding.root
    }


}