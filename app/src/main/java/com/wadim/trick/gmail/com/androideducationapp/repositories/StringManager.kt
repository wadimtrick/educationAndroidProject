package com.wadim.trick.gmail.com.androideducationapp.repositories

import android.content.Context
import com.example.coremodule.domain.IStringManager
import com.wadim.trick.gmail.com.androideducationapp.R
import javax.inject.Inject

class StringManager @Inject constructor(private val context: Context) : IStringManager {
    override fun getErrorText(): String = context.resources.getString(R.string.error)
}