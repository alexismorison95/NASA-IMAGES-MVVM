package com.morris.nasaimages.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat.getSystemService

class NoficationService {

    companion object {

        lateinit var notificationManager: NotificationManager
        lateinit var notificationChannel: NotificationChannel
        private lateinit var builder: Notification.Builder
        private const val channelId = "i.nasaiages.notifications"
        private const val description = "Download notification"

        const val SET_WALLP_FAILED = "Setting wallpaper failed"
        const val SET_WALLP_SUCCESS = "Wallpaper set successfully"
        const val ENJOY_WALLP = "Enjoy your wallpaper :)"
        const val DOWN_IMAGE_FAILED = "Download image failed"
        const val DOWN_IMAGE_SUCCESS = "Download image successfully"
        const val TRY_AGAIN = "Please try again"

        fun sendNotification(context: Context, title: String, content: String, icon: Int) {

            notificationManager = getSystemService(context, NotificationManager::class.java) as NotificationManager

            notificationChannel = NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification
                .Builder(context, channelId)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)

            notificationManager.notify(1234, builder.build())
        }
    }
}