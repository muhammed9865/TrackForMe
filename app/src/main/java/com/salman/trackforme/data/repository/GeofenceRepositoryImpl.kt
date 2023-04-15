package com.salman.trackforme.data.repository

import com.google.android.gms.maps.model.LatLng
import com.salman.trackforme.data.source.GeofenceSource
import com.salman.trackforme.domain.repository.GeofenceRepository
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class GeofenceRepositoryImpl @Inject constructor(
    private val geofenceSource: GeofenceSource,
) : GeofenceRepository {

    override suspend fun addGeofenceAt(latLng: LatLng, radius: Float): Result<Unit> {
        val isSet = geofenceSource.setGeofence(latLng, radius)
        return if (isSet) Result.success(Unit) else Result.failure(GeofenceNotSetException())
    }
}


class GeofenceNotSetException : Exception("Geofence not set")