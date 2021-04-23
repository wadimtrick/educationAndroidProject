package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.content.Context
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import com.wadim.trick.gmail.com.androideducationapp.views.ContactListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ContactListPresenter(private val context: Context) : MvpPresenter<ContactListView>() {
    private var scope: CoroutineScope? = null
    private var isFirstAttach = true

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setToolbarTitle()
    }

    override fun attachView(view: ContactListView?) {
        super.attachView(view)
        scope = CoroutineScope(Dispatchers.Main)
        if (isFirstAttach) {
            getContactList("")
            isFirstAttach = false
        }

    }

    override fun detachView(view: ContactListView?) {
        scope?.cancel()
        super.detachView(view)
    }

    fun getContactList(contactName: String) {
        scope?.launch {
            val contactList = ContactsSource(context).getContactList(contactName)
            viewState.showContactList(contactList)
        }
    }

    fun onClickShowDetails(contactId: String) {
        viewState.onClickShowDetails(contactId)
    }
}