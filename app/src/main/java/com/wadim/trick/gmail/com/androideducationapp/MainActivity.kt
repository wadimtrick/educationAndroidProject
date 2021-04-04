package com.wadim.trick.gmail.com.androideducationapp

import android.Manifest.permission.READ_CONTACTS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.*
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder

class MainActivity : AppCompatActivity(),
    ClickableContactListElement, ContactServiceClient {
    private lateinit var contactService: ContactService
    private var bound = false
    private var isSavedInstanceEmpty = true

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
        isSavedInstanceEmpty = savedInstanceState == null
        when (SDK_INT >= M && checkSelfPermission(READ_CONTACTS) == PERMISSION_DENIED) {
            true -> showPermissionWarningFragment()
            false -> showMainContent()
        }
        bindContactService()
        createBirthdayNotificationChannel()
    }

    private fun showMainContent() {
        val contactID = intent.getStringExtra(CONTACT_ID_KEY)
        if (!isSavedInstanceEmpty)
            return
        showContactList()
        if (contactID != null)
            showContactDetailsFragment(contactID)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ((requestCode == PERMISSION_REQUEST_CODE) && (!grantResults.contains(PERMISSION_DENIED)))
                showMainContent()
    }

    private fun showPermissionWarningFragment() {
        val fragment = PermissionWarningFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
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

    override fun showContactDetails(contactID: String) {
        showContactDetailsFragment(contactID)
    }

    private fun showContactDetailsFragment(contactID: String) {
        val fragment = ContactDetailsFragment.newInstance(contactID)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("contactDetails")
            .commit()
    }

    override suspend fun getContactListShortInfoWithService(): List<ContactShortInfo> {
        return contactService.getContactListShortInfo()
    }

    override suspend fun getContactFullInfoWithService(contactID: String): ContactFullInfo {
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