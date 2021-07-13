package com.example.coremodule.di

import androidx.fragment.app.Fragment


interface IFragmentInjector<T: Fragment> {
    fun inject(fragment: T)
}