package com.salman.trackforme.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.salman.trackforme.core.TrackFragment
import com.salman.trackforme.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }
}