package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentEditProfileBinding
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.utils.LocalDataStore
import com.alamin.bazar.view_model.UserViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EditProfileFragment"
class EditProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var localDataStore: LocalDataStore
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentEditProfileBinding
    private val arg by navArgs<EditProfileFragmentArgs>()
    private lateinit var user:User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectEditProfile(this)
        user = arg.user
        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this
        binding.user = user

        userViewModel.setUserData(user)

        binding.setOnSubmitClick {
            userViewModel.updateUser(object : APIResponse{
                override fun onSuccess(message: String) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                }

                override fun onFailed(message: String) {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        userViewModel.user.observe(requireActivity(), Observer {
            lifecycleScope.launch(IO) {
                localDataStore.storeUser(Gson().toJson(it))
            }
        })

        return binding.root
    }

}