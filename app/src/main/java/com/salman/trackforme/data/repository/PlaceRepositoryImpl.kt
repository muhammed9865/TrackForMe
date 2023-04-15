package com.salman.trackforme.data.repository

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.salman.trackforme.data.source.PlacesSource
import com.salman.trackforme.domain.model.Place
import com.salman.trackforme.domain.model.SearchPrediction
import com.salman.trackforme.domain.repository.PlaceRepository
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
class PlaceRepositoryImpl @Inject constructor(
    private val placesSource: PlacesSource
): PlaceRepository {

    override suspend fun search(query: String): List<SearchPrediction> {
        return placesSource.search(query).map { it.toDomainModel() }
    }

    override suspend fun getPlaceById(placeId: String): Place {
        return placesSource.getPlaceById(placeId).toDomainModel()
    }


    private fun AutocompletePrediction.toDomainModel(): SearchPrediction {
        return SearchPrediction(
            placeId = placeId,
            primaryText = getPrimaryText(null).toString(),
            secondaryText = getSecondaryText(null).toString()
        )
    }

    private fun com.google.android.libraries.places.api.model.Place.toDomainModel(): Place {
        return Place(
            id = id ?: Place.EMPTY_ID,
            name = name ?: Place.EMPTY_NAME,
            address = address ?: Place.EMPTY_ADDRESS,
            latitude = latLng?.latitude ?: Place.EMPTY_LATITUDE,
            longitude = latLng?.longitude ?: Place.EMPTY_LONGITUDE
        )
    }
}