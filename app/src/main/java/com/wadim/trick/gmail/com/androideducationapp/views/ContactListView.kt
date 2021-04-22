package com.wadim.trick.gmail.com.androideducationapp.views

import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ContactListView : MvpView {
    fun setToolbarTitle()
    fun showContactList(contactShortInfoList: List<ContactShortInfo>)
    fun setOnClickShowDetails(contactId: String)
    @StateStrategyType(OneExecutionStateStrategy::class)
    fun onClickShowDetails(contactId: String)
}