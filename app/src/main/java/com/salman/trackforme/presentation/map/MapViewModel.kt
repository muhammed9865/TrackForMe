package com.salman.trackforme.presentation.map

import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.salman.trackforme.core.TrackViewModel
import com.salman.trackforme.domain.usecase.GetPlaceByIdUseCase
import com.salman.trackforme.domain.usecase.SearchPlaceUseCase
import com.salman.trackforme.domain.usecase.SetGeofenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/8/2023.
 */
@HiltViewModel
class MapViewModel @Inject constructor(
    private val searchUseCase: SearchPlaceUseCase,
    private val getPlaceByIdUseCase: GetPlaceByIdUseCase,
    private val setGeofenceUseCase: SetGeofenceUseCase
) : TrackViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private var searchJob: Job? = null

    fun setMarkerAtLocation(latLng: LatLng) {
        _state.value = _state.value.copy(markerLocation = latLng)
    }

    fun search(query: String) = viewModelScope.launch {
        val isLoading = _state.value.isLoadingPredictions
        if (isLoading) return@launch

        if (query.isEmpty()) {
            return@launch
        }

        _state.value =
            _state.value.copy(
                isLoadingPredictions = true,
                searchPredictions = emptyList(),
                isCloseIconVisible = true
            )

        val result = searchUseCase(query).getOrDefault(emptyList())

        _state.value = _state.value.copy(
            searchPredictions = result,
            isLoadingPredictions = false,
            isCloseIconVisible = true
        )
    }.also {
        searchJob = it
    }

    fun clearSearch() {
        searchJob?.cancel()
        searchJob = null
        _state.value =
            _state.value.copy(searchPredictions = emptyList(), isCloseIconVisible = false)
    }

    fun goToPlace(placeId: String) = viewModelScope.launch {
        val result = getPlaceByIdUseCase.getPlaceById(placeId).getOrNull()
        result?.let {
            _state.value =
                _state.value.copy(
                    currentCameraLocation = LatLng(it.latitude, it.longitude),
                    searchPredictions = emptyList(),
                    isCloseIconVisible = false
                )
        }
    }

    fun setAlarm() {
        val location = _state.value.markerLocation ?: return
        viewModelScope.launch {
            setGeofenceUseCase(location, 100f)
            _state.value = _state.value.copy(geofenceLocation = location)
        }
    }


}