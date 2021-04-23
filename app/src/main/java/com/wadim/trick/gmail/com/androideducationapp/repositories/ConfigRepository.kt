package com.wadim.trick.gmail.com.androideducationapp.repositories

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import com.wadim.trick.gmail.com.androideducationapp.NOTIFICATION_CHANNEL_ID
import com.wadim.trick.gmail.com.androideducationapp.NOTIFICATION_CHANNEL_NAME

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

class ConfigRepository() {
    fun createBirthdayNotificationChannel(context: Context) {
        val channelID = NOTIFICATION_CHANNEL_ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    channelID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}