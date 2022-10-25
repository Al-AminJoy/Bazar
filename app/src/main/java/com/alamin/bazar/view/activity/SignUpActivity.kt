package com.alamin.bazar.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alamin.bazar.BazaarApplication

import com.alamin.bazar.databinding.ActivitySignUpBinding
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.view_model.UserViewModel
import com.alamin.bazar.view_model.ViewModelFactory
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
            binding.btnSignUp.visibility = View.GONE
            userViewModel.signUpUser(object : APIResponse {
                override fun onSuccess(message: String) {
                    Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
                    finish();
                }

                override fun onFailed(message: String) {
                    binding.btnSignUp.visibility = View.VISIBLE
                    Toast.makeText(this@SignUpActivity, message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        userViewModel.user.observe(this, Observer {
            Log.d(TAG, "onCreate: "+it)
        })

    }
}