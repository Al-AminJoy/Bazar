package com.alamin.bazar.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alamin.bazar.BazaarApplication

import com.alamin.bazar.databinding.ActivitySignUpBinding
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.view_model.UserViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SignUpActivity"
class SignUpActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val component = (this.applicationContext as BazaarApplication).appComponent
        component.injectSignUp(this)

        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        binding.setSignupClickListener {
            userViewModel.signUpUser()
        }

        lifecycleScope.launch {
            userViewModel.message.collect{
                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenCreated {
            userViewModel.user.collectLatest {
                when(it){
                    is Response.Loading -> {
                        binding.btnSignUp.visibility = View.GONE
                    }
                    is Response.Success -> {
                        it.data?.let {
                            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                            finish();
                        }
                    }
                    is Response.Error -> {
                        it.message?.let {
                            binding.btnSignUp.visibility = View.VISIBLE
                            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

    }
}