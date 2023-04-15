package com.salman.trackforme.core.location

import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2023.
 */
class LocationRequestCallback : LocationCallback() {

    private val location = MutableStateFlow(LatLng(0.0, 0.0))
    val locationFlow: StateFlow<LatLng> = location

    override fun onLocationResult(p0: LocationResult) {
        val latitude = p0.lastLocation?.latitude
        val longitude = p0.lastLocation?.longitude
        if (latitude != null && longitude != null) {
            location.value = LatLng(latitude, longitude)
        }
    }
}