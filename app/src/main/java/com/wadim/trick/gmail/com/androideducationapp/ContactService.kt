package com.wadim.trick.gmail.com.androideducationapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
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
        return@withContext ContactsSource(applicationContext).getContactList()
    }

    suspend fun getContactFullInfo(contactID: String): ContactFullInfo =
        withContext(coroutineContext) {

            return@withContext ContactsSource(applicationContext).getContactDetails(contactID)
        }
}

