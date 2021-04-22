package com.wadim.trick.gmail.com.androideducationapp.views

import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import moxy.MvpView
import moxy.viewstate.strategy.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface ContactDetailsView: MvpView {
    fun showContactDetails(contact: ContactFullInfo)
    fun setToolbarTitle()
}