package com.salman.trackforme.domain.usecase

import com.google.android.gms.maps.model.LatLng
import com.salman.trackforme.domain.repository.GeofenceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class SetGeofenceUseCase @Inject constructor(
    private val geofenceRepository: GeofenceRepository,
) {

    suspend operator fun invoke(latLng: LatLng, radius: Float) = withContext(Dispatchers.IO) {
        geofenceRepository.addGeofenceAt(latLng, radius)
    }
}