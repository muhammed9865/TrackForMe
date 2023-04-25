package com.salman.trackforme.data.source

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.salman.trackforme.core.common.hasBluetoothPermission
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
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/25/2023.
 */
class BluetoothSource @Inject constructor(
    @ApplicationContext private val context: Context,
) : CoroutineScope {
    private val bluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val devicesFlow = MutableSharedFlow<List<BluetoothDevice>>()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + Job()

    @Suppress("MissingPermission")
    fun getBluetoothDevices(): Flow<List<BluetoothDevice>> {
        bluetoothManager.adapter.startDiscovery()
        registerReceiver()
        return devicesFlow
    }

    private fun hasRequiredPermission(): Boolean {
        return context.hasBluetoothPermission()
    }

    private fun emitDevice(device: BluetoothDevice) = launch {
        devicesFlow.emit(listOf(device))
    }

    private fun registerReceiver() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(receiver, filter)
    }

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.let {
                val action = intent?.action
                if (BluetoothDevice.ACTION_FOUND == action) {
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    device?.let {
                        println(it.name)
                        emitDevice(it)
                    }
                }
            }
        }
    }
}