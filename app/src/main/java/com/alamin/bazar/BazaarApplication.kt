package com.alamin.bazar

import android.app.Application
import com.alamin.bazar.di.AppComponent
import com.alamin.bazar.di.DaggerAppComponent

class BazaarApplication : Application (){
lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
    appComponent = DaggerAppComponent.factory().create(this)
    }
}