package com.salman.trackforme.data

import android.content.Context
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.salman.trackforme.BuildConfig
import com.salman.trackforme.data.repository.BluetoothRepositoryImpl
import com.salman.trackforme.data.repository.GeofenceRepositoryImpl
import com.salman.trackforme.data.repository.PlaceRepositoryImpl
import com.salman.trackforme.data.source.BluetoothSource
import com.salman.trackforme.data.source.GeofenceSource
import com.salman.trackforme.data.source.PlacesSource
import com.salman.trackforme.domain.repository.BluetoothRepository
import com.salman.trackforme.domain.repository.GeofenceRepository
import com.salman.trackforme.domain.repository.PlaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePlaceClient(
        @ApplicationContext context: Context
    ): PlacesClient {
        if (!Places.isInitialized()) {
            val api = BuildConfig.MAPS_API_KEY
            Places.initialize(context, api)
        }
        return Places.createClient(context)
    }

    @Provides
    @Singleton
    fun provideGeoFenceClient(
        @ApplicationContext context: Context
    ): GeofencingClient {
        return LocationServices.getGeofencingClient(context)
    }


    @Provides
    @Singleton
    fun providePlacesSource(
        placesClient: PlacesClient
    ): PlacesSource {
        return PlacesSource(
            placesClient = placesClient,
            placeFields = providePlaceFields()
        )
    }


    @Provides
    @Singleton
    fun providePlaceRepository(
        placesSource: PlacesSource
    ): PlaceRepository {
        return PlaceRepositoryImpl(
            placesSource = placesSource
        )
    }


    @Provides
    @Singleton
    fun provideGeofenceRepository(
        source: GeofenceSource
    ): GeofenceRepository {
        return GeofenceRepositoryImpl(
            geofenceSource = source
        )
    }

    @Provides
    @Singleton
    fun provideBluetoothRepository(
        source: BluetoothSource
    ): BluetoothRepository {
        return BluetoothRepositoryImpl(
            source = source
        )
    }


    private fun providePlaceFields(): List<Place.Field> {
        return listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
    }
}