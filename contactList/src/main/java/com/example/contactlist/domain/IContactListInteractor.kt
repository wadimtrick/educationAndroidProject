package com.example.contactlist.domain

import com.example.coremodule.domain.ContactShortInfo
import io.reactivex.rxjava3.core.Single

interface IContactListInteractor {
    fun getContactList(contactName: String): Single<List<ContactShortInfo>>
    fun getErrorText(): String
}