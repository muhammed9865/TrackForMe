package com.salman.trackforme.domain.usecase

import android.bluetooth.BluetoothDevice
import com.salman.trackforme.domain.repository.BluetoothRepository
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class ConnectUseCase @Inject constructor(
    private val bluetoothRepository: BluetoothRepository
) {

    operator fun invoke(device: BluetoothDevice) {
        bluetoothRepository.connect(device)
    }
}