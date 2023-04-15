package com.salman.trackforme.data.source

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.maps.model.LatLng
import com.salman.trackforme.core.map.GeofenceReceiver
import com.salman.trackforme.data.extension.await
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class GeofenceSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: GeofencingClient,
) {

    companion object {
        private const val GEOFENCE_ID = "GEOFENCE_ID"
    }

    /**
     * @param location to set Geofence at
     * @param radius of Geofence
     * @return true if Geofence is set successfully
     */
    @Suppress("MissingPermission")
    suspend fun setGeofence(location: LatLng, radius: Float = 50f): Boolean {
        assertPermissions()

        val geofence = getGeofence(location, radius)
        val request = GeofencingRequest.Builder()
            .addGeofence(geofence)
            .build()

        client.removeGeofences(getPendingIntent(context)).await()

        return try {
            client.addGeofences(request, getPendingIntent(context)).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    private fun getGeofence(location: LatLng, radius: Float): Geofence {
        return Geofence.Builder()
            .setCircularRegion(location.latitude, location.longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .setRequestId(GEOFENCE_ID)
            .build()
    }


    private fun getPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, GeofenceReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            191,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun assertPermissions() {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )

        val missingPermissions = permissions.filter {
            context.checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED
        }

        if (missingPermissions.isNotEmpty()) {
            throw SecurityException("Missing permissions: $missingPermissions")
        }
    }
}