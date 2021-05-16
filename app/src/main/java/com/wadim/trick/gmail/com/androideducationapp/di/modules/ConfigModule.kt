package com.wadim.trick.gmail.com.androideducationapp.di.modules

import com.wadim.trick.gmail.com.androideducationapp.interfaces.IConfigRepository
import com.wadim.trick.gmail.com.androideducationapp.repositories.ConfigRepository
import dagger.Binds
import dagger.Module

@Module(includes = [AppModule::class])
interface ConfigModule {
    @Binds
    fun getIConfigRepository(configRepository: ConfigRepository): IConfigRepository
}