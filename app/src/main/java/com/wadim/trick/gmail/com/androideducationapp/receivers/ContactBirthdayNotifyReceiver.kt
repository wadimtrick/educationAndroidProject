package com.wadim.trick.gmail.com.androideducationapp.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.coremodule.domain.ContactBirthdayInfo
import com.wadim.trick.gmail.com.androideducationapp.CONTACT_BIRTHDAY_KEY
import com.wadim.trick.gmail.com.androideducationapp.CONTACT_ID_KEY
import com.wadim.trick.gmail.com.androideducationapp.CONTACT_NAME_KEY
import com.wadim.trick.gmail.com.androideducationapp.ContactBirthdayNotificationManager
import com.wadim.trick.gmail.com.androideducationapp.MainActivity
import com.wadim.trick.gmail.com.androideducationapp.NOTIFICATION_CHANNEL_ID
import com.wadim.trick.gmail.com.androideducationapp.R
import java.util.Calendar

private const val NOTIFICATION_ID = 101

class ContactBirthdayNotifyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null)
            return
        val contactBirthdayNotification = ContactBirthdayNotificationManager(context)
        val contactID = intent.getStringExtra(CONTACT_ID_KEY) ?: ""
        val contactBirthday = Calendar.getInstance()
        contactBirthday.timeInMillis = intent.getLongExtra(CONTACT_BIRTHDAY_KEY, 0)
        val contactName = intent.getStringExtra(CONTACT_NAME_KEY) ?: "Ошибка"
        if (contactName == "Ошибка")
            return

        createNotification(context, contactID, contactName)
        val contactBirthdayInfo =
                ContactBirthdayInfo(contactID, contactName, contactBirthday)
        contactBirthdayNotification.switchAlarmBithdayNotification(
                true,
                contactBirthdayInfo
        )
    }

    private fun createNotification(
            context: Context,
            contactID: String,
            contactName: String
    ) {
        val channelID = NOTIFICATION_CHANNEL_ID
        val notificationBuilder = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_birthday_cake)
                .setContentTitle(context.resources.getString(R.string.contact_birthday_notification_title))
                .setContentText(getBirthdayNotificationText(context, contactName))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getNotificationPendingIntent(context, contactID))

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getBirthdayNotificationText(context: Context, contactName: String): String {
        return String.format(
                context.resources.getString(R.string.contact_birthday_intent_text_value),
                contactName
        )
    }

    private fun getNotificationPendingIntent(context: Context, contactID: String): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
                .putExtra(CONTACT_ID_KEY, contactID)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        return PendingIntent.getActivity(
                context,
                contactID.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}