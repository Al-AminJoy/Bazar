package com.alamin.bazar.view.activity

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.ActivityMainBinding
import com.alamin.bazar.model.network.Response
import com.alamin.bazar.utils.Constants
import com.alamin.bazar.utils.LocalDataStore
import com.alamin.bazar.view_model.ProductViewModel
import com.alamin.bazar.view_model.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var localDataStore: LocalDataStore

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



        window.setBackgroundDrawable(ColorDrawable(getColor(R.color.theme_dark)))

        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.dashBoardFragment,R.id.cartFragment,R.id.profileFragment))
        setupActionBarWithNavController(navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.productDetailsFragment) {
                binding.navView.visibility = View.GONE
            }else if(destination.id == R.id.loadingFragment) {
                binding.navView.visibility = View.GONE
            } else {
                binding.navView.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navController.navigateUp(appBarConfiguration);
    }

    override fun onCreateOptionsMenu( menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.logOut -> {
                lifecycleScope.launch {
                    localDataStore.clearAllData()
                }
                startActivity(Intent(this@MainActivity,LoginActivity::class.java))
                finish()
                false
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }




}