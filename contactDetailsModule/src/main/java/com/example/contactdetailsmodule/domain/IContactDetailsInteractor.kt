package com.example.contactdetailsmodule.domain

import com.example.coremodule.domain.ContactBirthdayInfo
import com.example.coremodule.domain.ContactFullInfo
import io.reactivex.rxjava3.core.Single

interface IContactDetailsInteractor {
    fun getContactDetails(contactId: String): Single<ContactFullInfo>
    fun isBirthdayNotificationPendingIntentExist(contactBirthdayInfo: ContactBirthdayInfo): Boolean
    fun switchAlarmBirthdayNotification(
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    )

    fun getErrorText(): String
}