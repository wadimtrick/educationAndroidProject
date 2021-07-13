package com.wadim.trick.gmail.com.androideducationapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.coremodule.domain.ContactBirthdayInfo
import com.example.coremodule.domain.IBirthdayNotificationManager
import com.wadim.trick.gmail.com.androideducationapp.receivers.ContactBirthdayNotifyReceiver
import java.util.Calendar
import javax.inject.Inject

const val CONTACT_ID_KEY = "CONTACT_ID"
const val CONTACT_BIRTHDAY_KEY = "CONTACT_BIRTHDAY"
const val CONTACT_NAME_KEY = "CONTACT_NAME_KEY"
const val NOTIFICATION_CHANNEL_ID = "contactBirthdayChannel"
const val NOTIFICATION_CHANNEL_NAME = "Birthday notification channel"

class ContactBirthdayNotificationManager @Inject constructor(private val context: Context) :
    IBirthdayNotificationManager {
    override fun switchAlarmBithdayNotification(
        isNotificationEnabled: Boolean,
        contact: ContactBirthdayInfo
    ) {
        val birthday = contact.birthday ?: return

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notificationPendingIntent = getBirthdayNotificationPendingIntent(contact)

        if (isNotificationEnabled) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                getAlarmTime(birthday),
                notificationPendingIntent
            )
        } else {
            alarmManager.cancel(notificationPendingIntent)
            notificationPendingIntent.cancel()
        }
    }

    override fun isBirthdayNotificationPendingIntentExist(contact: ContactBirthdayInfo): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            contact.id.hashCode(),
            getBirthdayNotificationIntent(contact),
            PendingIntent.FLAG_NO_CREATE
        )
        return pendingIntent != null
    }

    private fun getBirthdayNotificationPendingIntent(contact: ContactBirthdayInfo): PendingIntent {
        return PendingIntent.getBroadcast(
            context,
            contact.id.hashCode(),
            getBirthdayNotificationIntent(contact),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getBirthdayNotificationIntent(contact: ContactBirthdayInfo): Intent {
        with(contact) {
            return Intent(context, ContactBirthdayNotifyReceiver::class.java)
                .putExtra(CONTACT_ID_KEY, id)
                .putExtra(CONTACT_BIRTHDAY_KEY, birthday?.timeInMillis)
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