package com.example.coremodule.di

import androidx.fragment.app.Fragment

interface IAppComponent {
    fun plusContactListComponent(): IFragmentInjector<out Fragment>
    fun plusContactDetailsComponent(): IFragmentInjector<out Fragment>
}