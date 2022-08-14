package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentProductDetailsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val TAG = "ProductDetailsFragment"
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding
    private  val arg by navArgs<ProductDetailsFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(layoutInflater)
        binding.product = arg.product
        return binding.root
    }

}