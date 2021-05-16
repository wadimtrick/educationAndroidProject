package com.wadim.trick.gmail.com.androideducationapp.di.modules

import com.wadim.trick.gmail.com.androideducationapp.ContactBirthdayNotificationManager
import com.wadim.trick.gmail.com.androideducationapp.interfaces.IBirthdayNotificationManager
import dagger.Binds
import dagger.Module

@Module
interface NotificationModule {
    @Binds
    fun getINotificationManager(contactBirthdayNotificationManager: ContactBirthdayNotificationManager): IBirthdayNotificationManager
}