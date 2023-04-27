package com.salman.trackforme.domain.repository

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.Flow

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/25/2023.
 */
interface BluetoothRepository {

    fun getBluetoothDevices(): Flow<List<BluetoothDevice>>
    fun connect(device: BluetoothDevice)

}