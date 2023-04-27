package com.salman.trackforme.presentation.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.lifecycle.viewModelScope
import com.salman.trackforme.core.TrackViewModel
import com.salman.trackforme.domain.usecase.ConnectUseCase
import com.salman.trackforme.domain.usecase.GetBluetoothDevicesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/25/2023.
 */
@SuppressLint("MissingPermission")
@HiltViewModel
class BluetoothViewModel @Inject constructor(
    private val getBluetoothDevicesUseCase: GetBluetoothDevicesUseCase,
    private val connectUseCase: ConnectUseCase
): TrackViewModel() {

    private val _devices = MutableStateFlow(emptyList<BluetoothDevice>())
    val devices = _devices.asStateFlow()

    @SuppressLint("MissingPermission")
    fun search() {
        viewModelScope.launch {
            getBluetoothDevicesUseCase()
                .onEach { devices ->
                    _devices.value = devices.map { it }
                }.launchIn(viewModelScope)
        }
    }

    fun connect(device: BluetoothDevice) {
        viewModelScope.launch {
            connectUseCase(device)
        }
    }
}