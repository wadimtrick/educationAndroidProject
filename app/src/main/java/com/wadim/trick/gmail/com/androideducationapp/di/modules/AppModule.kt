package com.wadim.trick.gmail.com.androideducationapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val context: Context) {
    @Provides
    fun getContext(): Context = context
}