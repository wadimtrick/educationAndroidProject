package com.wadim.trick.gmail.com.androideducationapp.views

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView: MvpView {
    fun showContactListFragment()
    fun showContactDetailsFragment(contactId: String)
    fun showPermissionWarningFragment()
}