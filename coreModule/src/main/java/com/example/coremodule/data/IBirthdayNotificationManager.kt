package com.example.coremodule.data

import com.example.coremodule.domain.ContactBirthdayInfo

interface IBirthdayNotificationManager {
    fun isBirthdayNotificationPendingIntentExist(contact: ContactBirthdayInfo): Boolean
    fun switchAlarmBithdayNotification(
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    )
}