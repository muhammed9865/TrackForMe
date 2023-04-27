package com.salman.trackforme.data.source

import android.content.Context
import com.salman.trackforme.core.common.hasNotificationPermission
import com.salman.trackforme.domain.model.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class SettingsSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : CoroutineScope {

    private val sharedPreferences =
        context.getSharedPreferences("settings", Context.MODE_PRIVATE).apply {
            registerOnSharedPreferenceChangeListener{ _, _ ->
                launch {
                    _flow.emit(getSettings())
                }
            }
        }

    private val _flow = MutableSharedFlow<Settings>()
    val flow: Flow<Settings> = _flow

    override val coroutineContext: CoroutineContext = Dispatchers.IO + Job()

    private fun getSettings(): Settings {
        return Settings(
            getZoom(),
            getRadius(),
            getNotification()
        )
    }

    fun getZoom(): Float {
        return sharedPreferences.getFloat("zoom", 0f)
    }

    fun getRadius(): Float {
        return sharedPreferences.getFloat("radius", 0f)
    }

    fun getNotification(): Boolean {
        return context.hasNotificationPermission()
    }

    fun setZoom(zoom: Float) {
        sharedPreferences.edit().putFloat("zoom", zoom).apply()
    }

    fun setRadius(radius: Float) {
        sharedPreferences.edit().putFloat("radius", radius).apply()
    }

    fun setNotification(notification: Boolean) {
        sharedPreferences.edit().putBoolean("notification", notification).apply()
    }
}