package com.salman.trackforme.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.salman.trackforme.core.TrackFragment
import com.salman.trackforme.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/21/2023.
 */
@AndroidEntryPoint
class SettingsFragment : TrackFragment<SettingsViewModel, FragmentSettingsBinding>(
    FragmentSettingsBinding::inflate
) {
    override val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        addObservers()
    }

    private fun setListeners() {
        binding.ZoomIn.setOnClickListener(){
            viewModel.incrementZoom()
        }

        binding.ZoomOut.setOnClickListener(){
            viewModel.decrementZoom()
        }

        binding.radiusIn.setOnClickListener(){
            viewModel.incrementRadius()
        }
        binding.radiusOut.setOnClickListener(){
            viewModel.decrementRadius()
        }
    }

    private fun addObservers() = lifecycleScope.launch {
        viewModel.flow.collect {
            binding.zoomValue.text = it.zoom.toString()
            binding.radiusValue.text = it.radius.toString()
        }
    }
}