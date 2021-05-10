package com.wadim.trick.gmail.com.androideducationapp.views

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ContactDetailsView: MvpView {
    fun showContactDetails(contact: ContactFullInfo)
    fun setToolbarTitle()
    fun setSwitchChecked(isChecked: Boolean)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setProgressBarVisible(isVisible: Boolean)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(text: String)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showMap(contactId: String)
}