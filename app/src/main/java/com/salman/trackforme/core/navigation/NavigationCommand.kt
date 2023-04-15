package com.salman.trackforme.core.navigation

import androidx.navigation.NavDirections

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/8/2023.
 */
sealed class NavigationCommand {
    data class To(val destination: NavDirections) : NavigationCommand()
    data class BackTo(val destinationId: Int, val inclusive: Boolean) : NavigationCommand()
    object Back : NavigationCommand()

}
