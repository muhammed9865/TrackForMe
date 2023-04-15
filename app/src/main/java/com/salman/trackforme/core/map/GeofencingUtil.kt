package com.salman.trackforme.core.map

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2023.
 */
object GeofencingUtil {

    private const val GEOFENCE_ID = "GEOFENCE_ID"

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun setGeofence(context: Context, location: LatLng, radius: Float = 1000f) {
        val client = LocationServices.getGeofencingClient(context)
        val geofence = getGeofence(location, radius)
        val request = GeofencingRequest.Builder()
            .addGeofence(geofence)
            .build()


        client.addGeofences(request, getPendingIntent(context)).run {
            addOnSuccessListener {
                println("Geofence added successfully")
            }
            addOnFailureListener {
                println("Geofence failed to add")
                it.printStackTrace()
            }
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
}