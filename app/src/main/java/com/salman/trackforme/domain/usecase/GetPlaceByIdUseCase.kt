package com.salman.trackforme.domain.usecase

import com.salman.trackforme.domain.model.Place
import com.salman.trackforme.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class GetPlaceByIdUseCase @Inject constructor(
    private val placeRepository: PlaceRepository
) {
    suspend fun getPlaceById(placeId: String): Result<Place> {
        return try {
            val result = placeRepository.getPlaceById(placeId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
