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
import com.alamin.bazar.databinding.FragmentEditProfileBinding

private const val TAG = "EditProfileFragment"
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val arg by navArgs<EditProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectEditProfile(this)
        Log.d(TAG, "onCreateView: ${arg.user}")
        return binding.root
    }

}