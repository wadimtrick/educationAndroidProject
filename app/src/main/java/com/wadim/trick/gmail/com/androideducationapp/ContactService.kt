package com.wadim.trick.gmail.com.androideducationapp

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.*
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

    suspend fun getContactListShortInfo(): ArrayList<ContactShortInfo> = withContext(
        coroutineContext) {
            delay(1500)
            return@withContext arrayListOf(ContactShortInfo(1, "Хрюня", "8-800-555-35-35", R.drawable.contact_photo))
    }

    suspend fun getContactFullInfo(contactID: Int): ContactFullInfo = withContext(coroutineContext) {
            delay(1500)
            return@withContext ContactFullInfo(
                1, "Хрюня", "8-800-555-35-35", "8-900-666-25-25",
                "hrunya@gmail.com", "-", "Описание", R.drawable.contact_photo
            )
    }
}

