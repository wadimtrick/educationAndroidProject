package com.wadim.trick.gmail.com.androideducationapp.di.components

import com.example.contactlist.di.ContactListComponent
import com.example.coremodule.di.IAppComponent
import com.wadim.trick.gmail.com.androideducationapp.AppDelegate
import com.wadim.trick.gmail.com.androideducationapp.MainActivity
import com.wadim.trick.gmail.com.androideducationapp.di.modules.AppModule
import com.wadim.trick.gmail.com.androideducationapp.di.modules.ConfigModule
import com.wadim.trick.gmail.com.androideducationapp.di.modules.ContactSourceModule
import com.wadim.trick.gmail.com.androideducationapp.di.modules.StringManagerModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ConfigModule::class, AppModule::class, ContactSourceModule::class, StringManagerModule::class])
interface AppComponent: IAppComponent {
    override fun plusContactDetailsComponent(): ContactDetailsComponent
    override fun plusContactListComponent(): ContactListComponent
    fun inject(appDelegate: AppDelegate)
    fun inject(mainActivity: MainActivity)
}