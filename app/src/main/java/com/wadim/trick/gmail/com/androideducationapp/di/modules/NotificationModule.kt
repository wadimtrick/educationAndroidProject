package com.wadim.trick.gmail.com.androideducationapp.di.modules

import com.example.coremodule.domain.IBirthdayNotificationManager
import com.wadim.trick.gmail.com.androideducationapp.ContactBirthdayNotificationManager
import dagger.Binds
import dagger.Module

@Module
interface NotificationModule {
    @Binds
    fun getINotificationManager(contactBirthdayNotificationManager: ContactBirthdayNotificationManager): IBirthdayNotificationManager
}