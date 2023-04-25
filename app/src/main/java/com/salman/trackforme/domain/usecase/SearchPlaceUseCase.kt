package com.salman.trackforme.domain.usecase

import com.salman.trackforme.domain.model.SearchPrediction
import com.salman.trackforme.domain.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class SearchPlaceUseCase @Inject constructor(
    private val placeRepository: PlaceRepository,
) {
    suspend operator fun invoke(query: String): Result<List<SearchPrediction>> {
        return try {
            withContext(Dispatchers.IO) {
                val result = placeRepository.search(query)
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}