package com.salman.trackforme.domain.usecase

import com.salman.trackforme.domain.repository.SettingsRepository
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2023.
 */
class GetSettingsFlowUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    operator fun invoke() = settingsRepository.flow
}