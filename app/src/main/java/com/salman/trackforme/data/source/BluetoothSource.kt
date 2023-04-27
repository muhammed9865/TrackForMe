package com.salman.trackforme.data.source

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Message
import com.salman.trackforme.core.common.hasBluetoothPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID
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

    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice) {
        val adapter = bluetoothManager.adapter
        try {
            val socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID())
            adapter.cancelDiscovery()
            socket.connect()
        } catch (e: IOException) {
            e.printStackTrace()
            println("error")
        }
    }

    private fun hasRequiredPermission(): Boolean {
        return context.hasBluetoothPermission()
    }

    private fun emitDevice(device: BluetoothDevice) = launch {
        devicesFlow.emit(listOf(device))
    }

    private fun registerReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        context.registerReceiver(receiver, filter)
    }

    private val receiver = object : BroadcastReceiver() {
        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.let {
                val action = intent?.action
                if (BluetoothDevice.ACTION_ACL_DISCONNECTED == action) {
                    bluetoothManager.adapter.startDiscovery()
                    // show alarm
                    println("disconnected")
                }

                if (BluetoothDevice.ACTION_ACL_CONNECTED == action) {
                    bluetoothManager.adapter.cancelDiscovery()
                    println("connected")
                }

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