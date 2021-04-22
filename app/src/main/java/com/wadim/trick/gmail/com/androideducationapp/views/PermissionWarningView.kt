package com.wadim.trick.gmail.com.androideducationapp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface PermissionWarningView: MvpView {
    fun setToolbarTitle()
    fun setButtonOnClick()
    fun onButtonClick()
}