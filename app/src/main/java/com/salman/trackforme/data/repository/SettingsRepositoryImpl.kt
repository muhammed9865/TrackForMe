package com.salman.trackforme.data.repository

import com.salman.trackforme.data.source.SettingsSource
import com.salman.trackforme.domain.model.Settings
import com.salman.trackforme.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class SettingsRepositoryImpl @Inject constructor(
    private val source: SettingsSource
) : SettingsRepository {

    override val flow: Flow<Settings>
        get() = source.flow

    override fun getZoom(): Float {
        return source.getZoom()
    }

    override fun getRadius(): Float {
        return source.getRadius()
    }

    override fun getNotification(): Boolean {
        return source.getNotification()
    }

    override fun setZoom(zoom: Float) {
        source.setZoom(zoom)
    }

    override fun setRadius(radius: Float) {
        source.setRadius(radius)
    }

    override fun setNotification(notification: Boolean) {
        source.setNotification(notification)
    }
}