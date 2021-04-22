package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.content.Context
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import com.wadim.trick.gmail.com.androideducationapp.views.ContactListView
import kotlinx.coroutines.*
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
        if (isFirstAttach) {
            scope = CoroutineScope(Dispatchers.Main)
            getContactList()
            isFirstAttach = false
        }

    }

    override fun detachView(view: ContactListView?) {
        scope?.cancel()
        super.detachView(view)
    }

    private fun getContactList() {
        scope?.launch {
            val contactList = ContactsSource(context).getContactList()
            viewState.showContactList(contactList)
        }
    }

    fun onClickShowDetails(contactId: String) {
        viewState.onClickShowDetails(contactId)
    }
}