package com.example.contactdetailsmodule.domain

import com.example.coremodule.domain.ContactBirthdayInfo
import com.example.coremodule.domain.IBirthdayNotificationManager
import com.example.coremodule.domain.IContactsSource
import com.example.coremodule.domain.IStringManager
import javax.inject.Inject

class ContactDetailsInteractor @Inject constructor(
    private val stringManager: IStringManager,
    private val contactBirthdayNotificationManager: IBirthdayNotificationManager,
    private val contactsSource: IContactsSource
) : IContactDetailsInteractor {
    override fun getContactDetails(contactId: String) = contactsSource.getContactDetails(contactId)
    override fun isBirthdayNotificationPendingIntentExist(contactBirthdayInfo: ContactBirthdayInfo) =
        contactBirthdayNotificationManager.isBirthdayNotificationPendingIntentExist(
            contactBirthdayInfo
        )

    override fun switchAlarmBirthdayNotification(
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    ) = contactBirthdayNotificationManager.switchAlarmBithdayNotification(
        isNotificationEnabled,
        contact
    )

    override fun getErrorText() = stringManager.getErrorText()
}