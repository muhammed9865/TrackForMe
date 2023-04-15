package com.salman.trackforme.core.location

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2023.
 */
object LocationUtil {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun getCurrentLocation(context: Context) = suspendCancellableCoroutine { continuation ->
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000,
        ).build()

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)


        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val latitude = result.lastLocation?.latitude
                val longitude = result.lastLocation?.longitude
                if (latitude != null && longitude != null) {
                    continuation.resume(LatLng(latitude, longitude))
                    fusedLocationProviderClient.removeLocationUpdates(this)
                } else {
                    continuation.resume(null)
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getCurrentLocationFlow(context: Context): Flow<LatLng>  {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            5000,
        ).build()

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        val callback = LocationRequestCallback()
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, null)

        return callback.locationFlow
    }


}