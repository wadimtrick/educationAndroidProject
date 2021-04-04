package com.wadim.trick.gmail.com.androideducationapp

interface ContactServiceClient {
    fun isServiceBinded(): Boolean
    suspend fun getContactListShortInfoWithService(): List<ContactShortInfo>
    suspend fun getContactFullInfoWithService(contactID: String): ContactFullInfo
}