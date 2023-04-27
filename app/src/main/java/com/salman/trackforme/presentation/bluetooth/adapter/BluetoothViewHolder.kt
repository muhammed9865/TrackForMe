package com.salman.trackforme.presentation.bluetooth.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.recyclerview.widget.RecyclerView
import com.salman.trackforme.databinding.ItemDeviceBinding

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class BluetoothViewHolder(
    private val binding: ItemDeviceBinding,
    private val onClick: (BluetoothDevice) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("MissingPermission")
    fun bind(item: BluetoothDevice) {
        binding.apply {
            textName.text = item.name
            root.setOnClickListener {
                onClick(item)
            }
        }
    }
}