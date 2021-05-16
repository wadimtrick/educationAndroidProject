package com.wadim.trick.gmail.com.androideducationapp.repositories

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import com.wadim.trick.gmail.com.androideducationapp.NOTIFICATION_CHANNEL_ID
import com.wadim.trick.gmail.com.androideducationapp.NOTIFICATION_CHANNEL_NAME
import com.wadim.trick.gmail.com.androideducationapp.interfaces.IConfigRepository
import javax.inject.Inject

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

class ConfigRepository @Inject constructor(private val context: Context) : IConfigRepository {
    override fun createBirthdayNotificationChannel() {
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkSelfPermission(permission: String): Int {
        return context.checkSelfPermission(permission)
    }
}