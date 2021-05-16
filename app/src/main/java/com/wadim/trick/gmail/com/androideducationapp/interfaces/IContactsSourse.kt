package com.wadim.trick.gmail.com.androideducationapp.interfaces

import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo
import io.reactivex.rxjava3.core.Single

interface IContactsSourse {
    fun getContactList(contactName: String): Single<List<ContactShortInfo>>
    fun getContactDetails(contactID: String): Single<ContactFullInfo>
}