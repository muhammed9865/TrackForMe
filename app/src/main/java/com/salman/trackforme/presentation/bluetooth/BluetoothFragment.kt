package com.salman.trackforme.presentation.bluetooth

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.salman.trackforme.core.TrackFragment
import com.salman.trackforme.databinding.FragmentBluetoothBinding
import com.salman.trackforme.presentation.bluetooth.adapter.BluetoothAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/25/2023.
 */
@AndroidEntryPoint
class BluetoothFragment : TrackFragment<BluetoothViewModel, FragmentBluetoothBinding>(
    FragmentBluetoothBinding::inflate
) {
    override val viewModel: BluetoothViewModel by viewModels()
    private val adapter by lazy {
        BluetoothAdapter {
            viewModel.connect(it)
        }
    }

        private val permissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                // guaranteed to be granted
                viewModel.search()
            }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            addObservers()
            requestBluetoothPermissions()
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

            binding.button.setOnClickListener {
                viewModel.search()
            }
        }

        private fun addObservers() {
            lifecycleScope.launch {
                viewModel.devices.collect {
                    println(it)
                    adapter.submitList(it)
                }
            }
        }

        private fun requestBluetoothPermissions() {
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                )
            } else {
                arrayOf(
                    Manifest.permission.BLUETOOTH
                )
            }
            if (permissions.any { requireContext().checkSelfPermission(it) != android.content.pm.PackageManager.PERMISSION_GRANTED }) {
                permissionRequest.launch(permissions)
            }

        }
    }