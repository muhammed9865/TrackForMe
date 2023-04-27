package com.salman.trackforme.domain.repository

import com.salman.trackforme.domain.model.Settings
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
interface SettingsRepository {

    val flow: Flow<Settings>

    fun getZoom(): Float
    fun getRadius(): Float
    fun getNotification(): Boolean

    fun setZoom(zoom: Float)
    fun setRadius(radius: Float)
    fun setNotification(notification: Boolean)
}