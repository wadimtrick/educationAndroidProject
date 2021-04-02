package com.wadim.trick.gmail.com.androideducationapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

const val CONTACT_ID_KEY = "CONTACT_ID"
const val CONTACT_PHOTO_ID_KEY = "CONTACT_PHOTO_ID"
const val CONTACT_BIRTHDAY_KEY = "CONTACT_BIRTHDAY"
const val CONTACT_NAME_KEY = "CONTACT_NAME_KEY"
const val NOTIFICATION_CHANNEL_ID = "contactBirthdayChannel"
const val NOTIFICATION_CHANNEL_NAME = "Birthday notification channel"

class ContactBirthdayNotificationManager() {
    fun switchAlarmBithdayNotification(
        context: Context,
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    ) {
        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationPendingIntent = getBirthdayNotificationPendingIntent(context, contact)

        if (isNotificationEnabled) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                getAlarmTime(contact.birthday),
                notificationPendingIntent
            )
        } else {
            alarmManager.cancel(notificationPendingIntent)
            notificationPendingIntent.cancel()
        }
    }

    fun isBirthdayNotificationPendingIntentExist(context: Context, contact: ContactBirthdayInfo): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            contact.id,
            getBirthdayNotificationIntent(context, contact),
            PendingIntent.FLAG_NO_CREATE
        )
        return pendingIntent != null
    }

    private fun getBirthdayNotificationPendingIntent(context: Context, contact: ContactBirthdayInfo): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            contact.id,
            getBirthdayNotificationIntent(context, contact),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getBirthdayNotificationIntent(context: Context, contact: ContactBirthdayInfo): Intent {
        with(contact) {
            return Intent(context, ContactBirthdayNotifyReceiver::class.java)
                .putExtra(CONTACT_ID_KEY, id)
                .putExtra(CONTACT_PHOTO_ID_KEY, imageId)
                .putExtra(CONTACT_BIRTHDAY_KEY, birthday.timeInMillis)
                .putExtra(CONTACT_NAME_KEY, name)
        }
    }

    private fun getAlarmTime(contactBirthday: Calendar): Long {
        contactBirthday.set(Calendar.HOUR_OF_DAY, 12)

        if (isBirthdayMoreThanToday(contactBirthday))
            contactBirthday.set(Calendar.YEAR, getToday().get(Calendar.YEAR))
        else
            contactBirthday.set(Calendar.YEAR, getToday().get(Calendar.YEAR) + 1)
        return contactBirthday.timeInMillis
    }

    private fun isBirthdayMoreThanToday(birthday: Calendar): Boolean =
        birthday.timeInMillis > getToday().timeInMillis

    private fun getToday(): Calendar {
        return Calendar.getInstance().apply {
            clear(Calendar.MILLISECOND)
            clear(Calendar.SECOND)
            clear(Calendar.MINUTE)
            clear(Calendar.HOUR)
        }
    }
}