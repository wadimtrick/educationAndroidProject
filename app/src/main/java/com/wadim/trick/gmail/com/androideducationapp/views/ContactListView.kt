package com.wadim.trick.gmail.com.androideducationapp.views

import com.example.coremodule.domain.ContactShortInfo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ContactListView : MvpView {
    fun setToolbarTitle()
    fun showContactList(contactShortInfoList: List<ContactShortInfo>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onClickShowDetails(contactId: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setProgressBarVisible(isVisible: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showToast(text: String)
}