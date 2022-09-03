package com.alamin.bazar.view.activity

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.app_interface.AddressSetListener
import com.alamin.bazar.databinding.ActivityMainBinding
import com.alamin.bazar.databinding.FragmentDashBoardBinding
import com.alamin.bazar.view.fragment.CartFragment
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var productViewModel: ProductViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.includeContent.findViewById(R.id.toolbar))

        val component = (this.applicationContext as BazaarApplication).appComponent
        component.injectMain(this)

        productViewModel = ViewModelProvider(this,viewModelFactory)[ProductViewModel::class.java]

        productViewModel.productList.observe(this, Observer {
            productViewModel.insertProduct(it)
        })

        window.setBackgroundDrawable(ColorDrawable(getColor(R.color.theme_dark)))

        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.dashBoardFragment,R.id.cartFragment,R.id.profileFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.productDetailsFragment) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp(appBarConfiguration);
    }

    override fun onResume() {
        super.onResume()
        productViewModel.requestProduct()
    }


}