package com.alamin.bazar.view.fragment

import android.os.Bundle
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
import com.alamin.bazar.databinding.FragmentEditProfileBinding
import com.alamin.bazar.model.data.User
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.utils.LocalDataStore
import com.alamin.bazar.viewmodel.EditProfileViewModel
import com.alamin.bazar.viewmodel.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "EditProfileFragment"
class EditProfileFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var localDataStore: LocalDataStore
    private lateinit var editProfileViewModel: EditProfileViewModel
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
        editProfileViewModel = ViewModelProvider(this,viewModelFactory)[EditProfileViewModel::class.java]

        binding.userViewModel = editProfileViewModel
        binding.lifecycleOwner = this
        binding.user = user

        editProfileViewModel.setUserData(user)

        binding.setOnSubmitClick {
            editProfileViewModel.updateUser()
        }

        lifecycleScope.launch {
            editProfileViewModel.message.collect {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }

        }
        lifecycleScope.launchWhenCreated {
            editProfileViewModel.user.collectLatest {
                when(it){
                    is Response.Loading -> {
                        findNavController().navigate(R.id.action_editProfileFragment_to_loadingFragment)
                    }
                    is Response.Success -> {
                        findNavController().popBackStack()
                        it.data.let {
                            lifecycleScope.launch(IO) {
                                localDataStore.storeUser(Gson().toJson(it))
                            }
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                        }
                    }
                    is Response.Error -> {
                        it.message?.let {
                            findNavController().popBackStack()
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }

}