package com.example.contactlist.domain

import com.example.coremodule.domain.IContactsSource
import com.example.coremodule.domain.IStringManager
import javax.inject.Inject

class ContactListInteractor @Inject constructor(
    private val stringManager: IStringManager,
    private val contactsSource: IContactsSource
) : IContactListInteractor {
    override fun getContactList(contactName: String) = contactsSource.getContactList(contactName)
    override fun getErrorText() = stringManager.getErrorText()
}