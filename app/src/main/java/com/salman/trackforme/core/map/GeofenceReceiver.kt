package com.salman.trackforme.core.map

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2023.
 */
@AndroidEntryPoint
class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            showNotification(it)
        }
    }

    private fun showNotification(context: Context) {
        createChannel(context)

        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Geofence triggered")
            .setContentText("Geofence triggered")
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .build()

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.notify(191, notification)
    }


    private fun createChannel(context: Context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "Location Alarm",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}