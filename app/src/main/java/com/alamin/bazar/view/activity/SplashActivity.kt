package com.alamin.bazar.view.activity

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alamin.bazar.BazaarApplication
import com.alamin.bazar.R
import com.alamin.bazar.databinding.ActivitySplashBinding
import com.alamin.bazar.utils.LocalDataStore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var localDataStore: LocalDataStore
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val component = (applicationContext as BazaarApplication).appComponent
        component.injectSplash(this)

        binding.animationView.animate().setDuration(1000).startDelay = 0
        binding.animationView.addAnimatorListener (object: Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                lifecycleScope.launchWhenCreated {
                    localDataStore.getUser().collect{
                        if (it.isEmpty() || it == null){
                            startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                            finish()
                        }else{
                            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                            finish()
                        }
                    }
                }

            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}

        })

    }
}