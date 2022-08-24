package com.alamin.bazar.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.ActivityLoginBinding
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.utils.LocalDataStore
import com.alamin.bazar.view_model.UserDataViewModel
import com.alamin.bazar.view_model.UserViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "LoginActivity"
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var localDataStore: LocalDataStore
    private lateinit var userDataViewModel: UserDataViewModel
    private lateinit var userViewModel: UserViewModel;
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val component = (this.applicationContext as BazaarApplication).appComponent
        component.injectLogin(this)

        userDataViewModel = ViewModelProvider(this,viewModelFactory)[UserDataViewModel::class.java]
        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]

        binding.userDataViewModel = userDataViewModel
        binding.lifecycleOwner = this

        binding.setLoginClickListener {
            binding.btnLogin.visibility = View.GONE
            userDataViewModel.loginUser(object : APIResponse{
                override fun onSuccess(message: String) {
                    //TODO: User ID Should Dynamic
                    userViewModel.requestUser(1);
                }

                override fun onFailed(message: String) {
                    binding.btnLogin.visibility = View.VISIBLE
                    Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        binding.setSignUpClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
        }

        userDataViewModel.loginResponse.observe(this, Observer {
            lifecycleScope.launch{
                localDataStore.storeToken(it.token)
            }
        })

        userViewModel.user.observe(this, Observer{
            if (it != null){
                lifecycleScope.launch {
                    localDataStore.storeUser(Gson().toJson(it))
                }
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
        })
    }


}