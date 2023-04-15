package com.salman.trackforme.domain.repository

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
interface GeofenceRepository {
    suspend fun addGeofenceAt(latLng: LatLng, radius: Float): Result<Unit>
}