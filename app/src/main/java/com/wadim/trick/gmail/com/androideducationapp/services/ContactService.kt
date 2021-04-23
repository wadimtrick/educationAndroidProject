package com.wadim.trick.gmail.com.androideducationapp.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactShortInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

private const val TAG = "ContactService"

class ContactService : Service() {
    private val contactBinder = ContactBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return contactBinder
    }

    inner class ContactBinder : Binder() {
        fun getService(): ContactService = this@ContactService
    }

    suspend fun getContactListShortInfo(): List<ContactShortInfo> = withContext(
            coroutineContext
    ) {
        return@withContext ContactsSource(applicationContext).getContactList("")
    }

    suspend fun getContactFullInfo(contactID: String): ContactFullInfo =
            withContext(coroutineContext) {

                return@withContext ContactsSource(applicationContext).getContactDetails(contactID)
            }
}

