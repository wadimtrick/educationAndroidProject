package com.wadim.trick.gmail.com.androideducationapp.interfaces

import com.wadim.trick.gmail.com.androideducationapp.models.ContactBirthdayInfo

interface IBirthdayNotificationManager {
    fun isBirthdayNotificationPendingIntentExist(contact: ContactBirthdayInfo): Boolean
    fun switchAlarmBithdayNotification(
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    )
}