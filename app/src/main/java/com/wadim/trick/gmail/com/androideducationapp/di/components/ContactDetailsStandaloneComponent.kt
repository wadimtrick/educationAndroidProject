package com.wadim.trick.gmail.com.androideducationapp.di.components

import android.content.Context
import com.wadim.trick.gmail.com.androideducationapp.di.modules.NotificationModule
import com.wadim.trick.gmail.com.androideducationapp.di.scopes.ContactDetailsScope
import dagger.BindsInstance
import dagger.Component

@ContactDetailsScope
@Component(modules = [NotificationModule::class])
interface ContactDetailsStandaloneComponent {
    //    fun inject(contactDetailsFragment: ContactDetailsFragment)
    @Component.Builder
    interface Builder {
        fun build(): ContactDetailsStandaloneComponent

        @BindsInstance
        fun setContext(context: Context): Builder
    }
}