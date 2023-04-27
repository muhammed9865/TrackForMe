package com.salman.trackforme.presentation.settings

import androidx.lifecycle.viewModelScope
import com.salman.trackforme.core.TrackViewModel
import com.salman.trackforme.domain.usecase.GetSettingsFlowUseCase
import com.salman.trackforme.domain.usecase.UpdateSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/21/2023.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    settingsFlow: GetSettingsFlowUseCase
): TrackViewModel() {

    val flow = settingsFlow()

    fun incrementZoom() = viewModelScope.launch {
        val settings = updateSettingsUseCase.getSettings()
        updateSettingsUseCase(zoom = settings.zoom.inc())
    }

    fun decrementZoom() = viewModelScope.launch {
        val settings = updateSettingsUseCase.getSettings()
        updateSettingsUseCase(zoom = settings.zoom.dec())
    }

    fun incrementRadius() = viewModelScope.launch {
        val settings = updateSettingsUseCase.getSettings()
        updateSettingsUseCase(radius = settings.radius.inc())
    }

    fun decrementRadius() = viewModelScope.launch {
        val settings = updateSettingsUseCase.getSettings()
        updateSettingsUseCase(radius = settings.radius.dec())
    }
}