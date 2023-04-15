package com.salman.trackforme.presentation.map

import com.google.android.gms.maps.model.LatLng
import com.salman.trackforme.domain.model.SearchPrediction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
data class MapState(
    val markerLocation: LatLng? = null,
    val isLoadingPredictions: Boolean = false,
    val searchPredictions: List<SearchPrediction> = emptyList(),
    val currentCameraLocation: LatLng? = null,
    val isCloseIconVisible: Boolean = false,
    val geofenceLocation: LatLng? = null,
)
