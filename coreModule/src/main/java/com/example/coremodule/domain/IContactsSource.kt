package com.example.coremodule.domain

import io.reactivex.rxjava3.core.Single

interface IContactsSource {
    fun getContactList(contactName: String): Single<List<ContactShortInfo>>
    fun getContactDetails(contactID: String): Single<ContactFullInfo>
}