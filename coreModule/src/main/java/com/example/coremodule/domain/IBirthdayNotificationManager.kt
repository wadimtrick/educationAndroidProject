package com.example.coremodule.domain

interface IBirthdayNotificationManager {
    fun isBirthdayNotificationPendingIntentExist(contact: ContactBirthdayInfo): Boolean
    fun switchAlarmBithdayNotification(
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    )
}