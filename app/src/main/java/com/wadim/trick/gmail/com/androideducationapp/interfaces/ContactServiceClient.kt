package com.wadim.trick.gmail.com.androideducationapp.interfaces

import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo

interface ContactServiceClient {
    fun isServiceBinded(): Boolean
    suspend fun getContactListShortInfoWithService(): List<ContactShortInfo>
    suspend fun getContactFullInfoWithService(contactID: String): ContactFullInfo
}