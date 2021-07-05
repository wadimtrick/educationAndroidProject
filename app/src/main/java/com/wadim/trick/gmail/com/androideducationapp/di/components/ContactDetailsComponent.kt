package com.wadim.trick.gmail.com.androideducationapp.di.components

import com.wadim.trick.gmail.com.androideducationapp.di.modules.NotificationModule
import com.wadim.trick.gmail.com.androideducationapp.di.scopes.ContactDetailsScope
import com.wadim.trick.gmail.com.androideducationapp.fragments.ContactDetailsFragment
import dagger.BindsInstance
import dagger.Subcomponent

@ContactDetailsScope
@Subcomponent(modules = [NotificationModule::class])
interface ContactDetailsComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ContactDetailsComponent

        @BindsInstance
        fun contactID(contactID: String): Builder
    }

    fun inject(contactDetailsFragment: ContactDetailsFragment)
}