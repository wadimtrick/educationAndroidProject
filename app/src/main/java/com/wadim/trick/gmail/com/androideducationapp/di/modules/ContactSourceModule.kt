package com.wadim.trick.gmail.com.androideducationapp.di.modules

import com.example.coremodule.domain.IContactsSource
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule::class])
interface ContactSourceModule {
    @Binds
    fun getIContactsSource(contactsSource: ContactsSource): IContactsSource
}