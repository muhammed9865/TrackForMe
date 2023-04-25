package com.salman.trackforme.data.source

import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.salman.trackforme.data.extension.await
import javax.inject.Inject

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
private const val TAG = "PlacesSource"

class PlacesSource @Inject constructor(
    private val placesClient: PlacesClient,
    private val placeFields: List<Place.Field>,
) {

    suspend fun search(query: String): List<AutocompletePrediction> {
        val request = FindAutocompletePredictionsRequest.newInstance(query)
        val response = placesClient.findAutocompletePredictions(request).await()

        return response.autocompletePredictions
    }

    suspend fun getPlaceById(placeId: String): Place {
        val request = FetchPlaceRequest.newInstance(placeId, placeFields)
        val response = placesClient.fetchPlace(request).await()

        return response.place
    }
}