package com.salman.trackforme.domain.repository

import com.salman.trackforme.domain.model.Place
import com.salman.trackforme.domain.model.SearchPrediction

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
interface PlaceRepository {

    suspend fun search(query: String): List<SearchPrediction>

    suspend fun getPlaceById(placeId: String): Place
}