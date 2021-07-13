package com.wadim.trick.gmail.com.androideducationapp

import android.app.Application
import com.example.coremodule.di.IAppDelegate
import com.wadim.trick.gmail.com.androideducationapp.di.components.AppComponent
import com.wadim.trick.gmail.com.androideducationapp.di.components.DaggerAppComponent
import com.wadim.trick.gmail.com.androideducationapp.di.modules.AppModule

class AppDelegate : Application(), IAppDelegate {
    lateinit var appComponent: AppComponent

    override fun getIAppComponent() = appComponent

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()
    }

}