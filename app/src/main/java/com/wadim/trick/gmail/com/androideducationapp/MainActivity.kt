package com.wadim.trick.gmail.com.androideducationapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder

class MainActivity : AppCompatActivity(),
    ClickableContactListElement, ContactServiceClient {
    private lateinit var contactService: ContactService
    private var bound = false

    private val contactServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            bound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = (service as? ContactService.ContactBinder) ?: return
            contactService = binder.getService()
            bound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            showContactList()
        bindContactService()
    }

    override fun onDestroy() {
        if (bound)
            unbindService(contactServiceConnection)
        super.onDestroy()
    }

    private fun showContactList() {
        val fragment = ContactListElementFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun bindContactService() {
        val intent = Intent(this, ContactService::class.java)
        bindService(intent, contactServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun showContactDetails(contactID: Int) {
        showContactDetailsFragment(contactID)
    }

    private fun showContactDetailsFragment(contactID: Int) {
        val fragment = ContactDetailsFragment.newInstance(contactID)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("contactDetails")
            .commit()
    }

    override suspend fun getContactListShortInfoWithService(): List<ContactShortInfo> {
        return contactService.getContactListShortInfo()
    }

    override suspend fun getContactFullInfoWithService(contactID: Int): ContactFullInfo {
        return contactService.getContactFullInfo(contactID)
    }

    override fun isServiceBinded() = bound
}