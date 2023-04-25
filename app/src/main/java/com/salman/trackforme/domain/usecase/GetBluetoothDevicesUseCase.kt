package com.salman.trackforme.domain.usecase

import android.bluetooth.BluetoothDevice
import com.salman.trackforme.domain.repository.BluetoothRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/25/2023.
 */
class GetBluetoothDevicesUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
){
    operator fun invoke(): Flow<List<BluetoothDevice>> {
        return bluetoothRepository.getBluetoothDevices()
    }
}