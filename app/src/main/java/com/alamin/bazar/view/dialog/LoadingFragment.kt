package com.alamin.bazar.view.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentLoadingBinding


class LoadingFragment : DialogFragment() {
   private lateinit var binding: FragmentLoadingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(layoutInflater)

        binding.animationView.animate().startDelay = 0
        dialog?.setCanceledOnTouchOutside(false)
        return binding.root
    }


}