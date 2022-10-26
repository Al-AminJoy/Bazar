package com.alamin.bazar.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.databinding.ActivityLoginBinding
import com.alamin.bazar.model.network.APIResponse
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.utils.LocalDataStore
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
    private lateinit var userViewModel: UserViewModel;
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val component = (this.applicationContext as BazaarApplication).appComponent
        component.injectLogin(this)

        userViewModel = ViewModelProvider(this,viewModelFactory)[UserViewModel::class.java]

        binding.userViewModel = userViewModel
        binding.lifecycleOwner = this

        binding.setLoginClickListener {
            userViewModel.loginUser()
        }

        binding.setSignUpClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
        }

        userViewModel.dummyLogin()
        //userViewModel.requestUser(1);
        userViewModel.loginResponse.observe(this, Observer {
            when(it){
                is Response.Loading ->{
                    binding.btnLogin.visibility = View.GONE
                }
                is Response.Success ->{
                    it.data?.let {
                        userViewModel.requestUser(1)
                        lifecycleScope.launch{
                            localDataStore.storeToken(it.token)
                        }
                    }
                }
                is Response.Error ->{
                    it.message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        binding.btnLogin.visibility = View.VISIBLE
                    }
                }
            }
        })

        userViewModel.user.observe(this, Observer{
            when(it){
                is Response.Loading ->{}
                is Response.Success ->{
                    it.data?.let {
                        lifecycleScope.launch {
                            localDataStore.storeUser(Gson().toJson(it))
                        }
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        finish()
                    }
                }
                is Response.Error ->{
                    it.message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        binding.btnLogin.visibility = View.VISIBLE
                    }
                }
            }
        })
    }


}