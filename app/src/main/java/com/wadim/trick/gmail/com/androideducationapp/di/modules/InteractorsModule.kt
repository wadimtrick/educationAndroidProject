package com.wadim.trick.gmail.com.androideducationapp.di.modules

import com.example.contactdetailsmodule.domain.ContactDetailsInteractor
import com.example.contactdetailsmodule.domain.IContactDetailsInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorsModule {
    @Binds
    fun getDetailsInteractor(contactDetailsInteractor: ContactDetailsInteractor): IContactDetailsInteractor
}