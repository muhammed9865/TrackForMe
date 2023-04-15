package com.salman.trackforme.core

import androidx.lifecycle.ViewModel
import com.salman.trackforme.core.navigation.NavigationCommand
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/8/2023.
 */
abstract class TrackViewModel : ViewModel() {

    private val _navigationCommands = MutableStateFlow<NavigationCommand?>(null)
    val navigationCommands: StateFlow<NavigationCommand?> = _navigationCommands

    fun navigate(navigationCommand: NavigationCommand) {
        _navigationCommands.value = navigationCommand
    }

}