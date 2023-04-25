package com.salman.trackforme.core.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.fragment.app.Fragment

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/21/2023.
 */
fun Context.hasLocationPermission(): Boolean {
    return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED
            && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED
}

fun Fragment.hasLocationPermission(): Boolean {
    return requireContext().hasLocationPermission()
}

fun Context.hasBluetoothPermission(): Boolean {
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_ADVERTISE,
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH
        )
    }
    return permissions.all {
        checkSelfPermission(it) == PERMISSION_GRANTED
    }
}