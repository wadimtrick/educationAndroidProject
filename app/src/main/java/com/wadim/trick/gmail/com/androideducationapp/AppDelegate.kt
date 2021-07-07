package com.wadim.trick.gmail.com.androideducationapp

import android.app.Application
import com.wadim.trick.gmail.com.androideducationapp.di.components.AppComponent
import com.wadim.trick.gmail.com.androideducationapp.di.components.ContactDetailsStandaloneComponent
import com.wadim.trick.gmail.com.androideducationapp.di.components.DaggerAppComponent
import com.wadim.trick.gmail.com.androideducationapp.di.components.DaggerContactDetailsStandaloneComponent
import com.wadim.trick.gmail.com.androideducationapp.di.modules.AppModule

class AppDelegate : Application() {
    lateinit var appComponent: AppComponent
    lateinit var contactDetailsStandaloneComponent: ContactDetailsStandaloneComponent

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .build()

        contactDetailsStandaloneComponent = DaggerContactDetailsStandaloneComponent.builder()
            .setContext(applicationContext)
            .build()

    }

}