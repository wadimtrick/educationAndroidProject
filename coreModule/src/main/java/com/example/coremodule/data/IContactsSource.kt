package com.example.coremodule.data

import com.example.coremodule.domain.ContactFullInfo
import com.example.coremodule.domain.ContactShortInfo
import io.reactivex.rxjava3.core.Single

interface IContactsSource {
    fun getContactList(contactName: String): Single<List<ContactShortInfo>>
    fun getContactDetails(contactID: String): Single<ContactFullInfo>
}