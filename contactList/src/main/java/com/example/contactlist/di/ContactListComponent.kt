package com.example.contactlist.di

import com.example.contactlist.presentation.ContactListFragment
import com.example.coremodule.di.IFragmentInjector
import dagger.Subcomponent

@ContactListScope
@Subcomponent
interface ContactListComponent: IFragmentInjector<ContactListFragment>