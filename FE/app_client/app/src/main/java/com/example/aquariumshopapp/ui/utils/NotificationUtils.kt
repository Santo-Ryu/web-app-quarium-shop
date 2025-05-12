package com.example.aquariumshopapp.ui.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.aquariumshopapp.R

class NotificationUtils {
    companion object {
        fun showNotification(context: Context, message: String, channelId: String = "default_channel", iconResId: Int = R.drawable.bell_solid) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    channelId,
                    "General Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Notifications for general use."
                }
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(context, channelId)
                .setContentTitle("Aquarium Shop")
                .setContentText(message)
                .setSmallIcon(iconResId)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(0, notification)
        }
    }
}