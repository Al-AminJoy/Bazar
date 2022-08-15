package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentCheckoutBinding

private const val TAG = "CheckoutFragment"
class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding

    private val arg by navArgs<CheckoutFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckoutBinding.inflate(layoutInflater)

        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectCheckout(this)
        Log.d(TAG, "onCreateView: ${arg.invoice}")
        return binding.root
    }


}