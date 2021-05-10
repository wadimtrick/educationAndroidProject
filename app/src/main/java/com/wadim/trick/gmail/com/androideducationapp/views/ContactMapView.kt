package com.wadim.trick.gmail.com.androideducationapp.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ContactMapView: MvpView {
    fun setToolbarTitle()
}