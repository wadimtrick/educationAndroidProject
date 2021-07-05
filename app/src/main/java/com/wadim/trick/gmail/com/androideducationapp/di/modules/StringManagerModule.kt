package com.wadim.trick.gmail.com.androideducationapp.di.modules

import com.wadim.trick.gmail.com.androideducationapp.interfaces.IStringManager
import com.wadim.trick.gmail.com.androideducationapp.repositories.StringManager
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule::class])
interface StringManagerModule {
    @Binds
    fun getIStringManager(stringManager: StringManager): IStringManager
}