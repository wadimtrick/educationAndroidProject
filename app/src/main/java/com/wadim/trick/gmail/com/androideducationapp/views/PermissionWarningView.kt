package com.wadim.trick.gmail.com.androideducationapp.views

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface PermissionWarningView: MvpView {
    fun setToolbarTitle()
    fun onButtonClick()
}