package com.salman.trackforme.core.map

import android.content.Context
import android.graphics.Color
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.salman.trackforme.R
import java.util.Collections.addAll

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/7/2023.
 */
object MapUtil {
    private var latestMarker: Marker? = null
    private var latestCircle: Circle? = null

    fun setMarker(context: Context, map: GoogleMap, latLng: LatLng) {
        latestMarker?.remove()

        val markerBitmap = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_marker_arrow, null)?.toBitmap()
        val icon = markerBitmap?.let { BitmapDescriptorFactory.fromBitmap(it) }

        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(icon)

        latestMarker = map.addMarker(markerOptions)

    }

    fun setCircle(context: Context, map: GoogleMap, latLng: LatLng, radius: Float = 50f) {
        latestCircle?.remove()

        val circleOptions = CircleOptions()
            .center(latLng)
            .radius(radius.toDouble())
            .fillColor(context.getColor(R.color.yellow_alpha_60))
            .strokeWidth(0f)

        latestCircle = map.addCircle(circleOptions)
    }


    fun goToLocation(map: GoogleMap, latLng: LatLng, zoom: Float = 15f) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom)
        map.animateCamera(cameraUpdate)
    }

    fun getMapStyle(context: Context): MapStyleOptions {
        return MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style)
    }
}
