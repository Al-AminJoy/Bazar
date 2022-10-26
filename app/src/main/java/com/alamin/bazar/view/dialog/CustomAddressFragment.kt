package com.alamin.bazar.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.databinding.FragmentCustomAddressBinding
import com.alamin.bazar.utils.LocalDataStore
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomAddressFragment : DialogFragment() {
    @Inject
    lateinit var localDataStore:LocalDataStore

    private lateinit var binding: FragmentCustomAddressBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomAddressBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectCustomAddress(this)

        binding.setOnSubmitClick {
            val address = binding.txtAddress.text.toString()
            if (address.trim().isEmpty()){
                Toast.makeText(requireContext(), "Please, Enter Address First", Toast.LENGTH_SHORT).show()
            }else{
               // val action = CustomAddressFragmentDirections.actionCustomAddressFragmentToCartFragment(address)
                lifecycleScope.launch {
                    localDataStore.storeLastAddress(address);
                    dismiss()
                }
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog.let {
            // Set Match Parent for Full Screen Dialog
            val width: Int = ViewGroup.LayoutParams.MATCH_PARENT;
            val height: Int = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog?.window?.setLayout(width, height)
        }
    }
}