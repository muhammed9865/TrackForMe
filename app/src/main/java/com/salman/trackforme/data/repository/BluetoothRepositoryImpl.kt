package com.salman.trackforme.data.repository

import android.bluetooth.BluetoothDevice
import com.salman.trackforme.data.source.BluetoothSource
import com.salman.trackforme.domain.repository.BluetoothRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/25/2023.
 */
class BluetoothRepositoryImpl @Inject constructor(
    private val source: BluetoothSource
): BluetoothRepository {

    override fun getBluetoothDevices(): Flow<List<BluetoothDevice>> {
        return source.getBluetoothDevices()
    }

    override fun connect(device: BluetoothDevice) {
        source.connect(device)
    }
}