package com.wadim.trick.gmail.com.androideducationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log

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
        bindContactService()
        val contactID = intent.getIntExtra(CONTACT_ID_KEY, 0)
        if (savedInstanceState == null) {
            showContactList()
            if (contactID > 0)
                showContactDetailsFragment(contactID)
        }
        createBirthdayNotificationChannel()
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

    private fun createBirthdayNotificationChannel() {
        val channelID = NOTIFICATION_CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}