package com.wadim.trick.gmail.com.androideducationapp.di.components

import com.example.contactdetailsmodule.presentation.ContactDetailsFragment
import com.example.coremodule.di.IFragmentInjector
import com.wadim.trick.gmail.com.androideducationapp.di.modules.InteractorsModule
import com.wadim.trick.gmail.com.androideducationapp.di.modules.NotificationModule
import com.wadim.trick.gmail.com.androideducationapp.di.scopes.ContactDetailsScope
import dagger.Subcomponent

@ContactDetailsScope
@Subcomponent(modules = [NotificationModule::class, InteractorsModule::class])
interface ContactDetailsComponent: IFragmentInjector<ContactDetailsFragment>