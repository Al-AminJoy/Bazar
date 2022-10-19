package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentEditProfileBinding
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.view_model.UserViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import javax.inject.Inject

private const val TAG = "EditProfileFragment"
class EditProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentEditProfileBinding
    private val arg by navArgs<EditProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectEditProfile(this)
        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        binding.user = arg.user

        binding.setOnSubmitClick {
            userViewModel.updateUser(object : APIResponse{
                override fun onSuccess(message: String) {

                }

                override fun onFailed(message: String) {

                }

            })
        }

        return binding.root
    }

}