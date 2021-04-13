package com.wadim.trick.gmail.com.androideducationapp.presenters

import android.content.Context
import android.widget.Switch
import androidx.core.view.isVisible
import com.wadim.trick.gmail.com.androideducationapp.ContactBirthdayNotificationManager
import com.wadim.trick.gmail.com.androideducationapp.models.ContactBirthdayInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactFullInfo
import com.wadim.trick.gmail.com.androideducationapp.models.ContactsSource
import com.wadim.trick.gmail.com.androideducationapp.views.ContactDetailsView
import kotlinx.coroutines.*
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ContactDetailsPresenter(private val context: Context, private val contactId: String) : MvpPresenter<ContactDetailsView>() {
    private var scope: CoroutineScope? = null
    private var isFirstAttach = true

    override fun attachView(view: ContactDetailsView?) {
        super.attachView(view)
        if (isFirstAttach) {
            viewState.setToolbarTitle()
            isFirstAttach = false
        }
        scope = CoroutineScope(Dispatchers.Main)
        setContactDetailsContent()
    }

    override fun detachView(view: ContactDetailsView?) {
        scope?.cancel()
        super.detachView(view)
    }

    private fun setContactDetailsContent() {
        scope?.launch {
            val contactFullInfo = ContactsSource(context).getContactDetails(contactId)
            viewState.showContactDetails(contactFullInfo)
        }
    }

    fun setNotifySwitchListener(contactFullInfo: ContactFullInfo, switch: Switch) {
        val contactBirthdayInfo = ContactBirthdayInfo(contactFullInfo)
        if (contactFullInfo.birthday == null)
            return
        switch?.isVisible = true
        with(ContactBirthdayNotificationManager()) {
            if (isBirthdayNotificationPendingIntentExist(context, contactBirthdayInfo))
                switch?.isChecked = true
            switch?.setOnCheckedChangeListener { buttonView, isChecked ->
                switchAlarmBithdayNotification(
                        context,
                        isChecked,
                        contactBirthdayInfo
                )
            }
        }
    }
}