package com.alamin.bazar.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.FragmentProfileBinding
import com.alamin.bazar.model.data.User
import com.alamin.bazar.utils.LocalDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

private const val TAG = "ProfileFragment"
class ProfileFragment : Fragment() {

    @Inject
    lateinit var localDataStore: LocalDataStore

    private lateinit var binding: FragmentProfileBinding
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val component = (requireActivity().applicationContext as BazaarApplication).appComponent
        component.injectProfile(this)
        lifecycleScope.launchWhenCreated {
            localDataStore.getUser().collect{
                user = Gson().fromJson(it,User::class.java)
                binding.user = user
            }
        }

        binding.setOnOrdersClick {
            findNavController().navigate(R.id.action_profileFragment_to_ordersFragment)
        }

        binding.setOnWishListClick {
            findNavController().navigate(R.id.action_profileFragment_to_wishListFragment)
        }

        binding.setOnEditClick {
            val action = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(user)
            findNavController().navigate(action)
        }

        return binding.root
    }

}