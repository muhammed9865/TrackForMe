package com.salman.trackforme.presentation.bluetooth.adapter

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.salman.trackforme.databinding.ItemDeviceBinding

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class BluetoothAdapter(
    private val onClick: (BluetoothDevice) -> Unit
) : ListAdapter<BluetoothDevice, BluetoothViewHolder>(Callback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothViewHolder {
        return BluetoothViewHolder(
            ItemDeviceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: BluetoothViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


object Callback: DiffUtil.ItemCallback<BluetoothDevice>() {
    override fun areItemsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: BluetoothDevice, newItem: BluetoothDevice): Boolean {
        return oldItem == newItem
    }
}