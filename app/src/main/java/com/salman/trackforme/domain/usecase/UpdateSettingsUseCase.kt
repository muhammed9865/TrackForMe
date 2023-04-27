package com.salman.trackforme.domain.usecase

import com.salman.trackforme.domain.model.Settings
import com.salman.trackforme.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class UpdateSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(
        zoom: Float = settingsRepository.getZoom(),
        radius: Float = settingsRepository.getRadius(),
        notification: Boolean = settingsRepository.getNotification()
    ) {
        settingsRepository.setZoom(zoom)
        settingsRepository.setRadius(radius)
        settingsRepository.setNotification(notification)
    }

    fun getSettings(): Settings {
        return Settings(
            settingsRepository.getZoom(),
            settingsRepository.getRadius(),
            settingsRepository.getNotification()
        )
    }
}