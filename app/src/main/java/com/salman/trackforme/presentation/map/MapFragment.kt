package com.salman.trackforme.presentation.map

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.awaitMap
import com.salman.trackforme.R
import com.salman.trackforme.core.TrackFragment
import com.salman.trackforme.core.common.hasLocationPermission
import com.salman.trackforme.core.map.MapUtil
import com.salman.trackforme.databinding.FragmentMapBinding
import com.salman.trackforme.domain.model.SearchPrediction
import com.salman.trackforme.presentation.map.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/8/2023.
 */
@AndroidEntryPoint
class MapFragment : TrackFragment<MapViewModel, FragmentMapBinding>(
    FragmentMapBinding::inflate
) {

    override val viewModel: MapViewModel by viewModels()
    private var map: GoogleMap? = null

    private val groupPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            map?.setUiSettings()
        }

    private val singlePermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            map?.setUiSettings()
        }

    private val RecyclerView.searchAdapter get() = adapter as SearchAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
        setupRecyclerView()
        initMap()
        setListeners()
        addObservers()

    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = SearchAdapter { searchPrediction ->
            viewModel.goToPlace(searchPrediction.placeId)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.cardViewSearch
            .layoutTransition
            .enableTransitionType(LayoutTransition.CHANGING)
    }

    private fun addObservers() = lifecycleScope.launch {
        viewModel.state
            .onEach(::handleState)
            .launchIn(this)
    }

    private fun setListeners() = with(binding) {

        // set a focus listener on searchEditText, if it is focused, increase alpha of searchCardView else decrease it
        editTextSearch.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                cardViewSearch.animate().alpha(1f).start()
            } else {
                cardViewSearch.animate().alpha(0.5f).start()
            }
        }

        cardViewAlarm.setOnClickListener {
            viewModel.setAlarm()
        }
    }

    private fun initMap() = lifecycleScope.launch {
        val mapFragment = binding.map.getFragment<SupportMapFragment>()

        mapFragment.awaitMap().apply {
            map = this
            onMapReady()
        }
    }

    @SuppressLint("MissingPermission")
    private fun onMapReady() {
        map?.setUiSettings()

        map?.setOnMapClickListener { latLng ->
            viewModel.setMarkerAtLocation(latLng)
        }
    }

    @SuppressLint("MissingPermission")
    private fun GoogleMap.setUiSettings() {
        uiSettings.isMapToolbarEnabled = true
        uiSettings.isZoomControlsEnabled = true
        uiSettings.isCompassEnabled = true
        uiSettings.isRotateGesturesEnabled = true
        uiSettings.isScrollGesturesEnabled = true
        uiSettings.isTiltGesturesEnabled = true

        if (hasLocationPermission()) {
            isMyLocationEnabled = true
        }
        isBuildingsEnabled = true
        isTrafficEnabled = true

        setMapStyle(MapUtil.getMapStyle(requireContext()))

    }

    private fun handleState(state: MapState) {
        handleMarkerLocation(state.markerLocation)
        state.currentCameraLocation?.let { handleCameraLocation(it) }
        handleSearchPredictions(state.isLoadingPredictions, state.searchPredictions)
        handleCloseIcon(state.isCloseIconVisible)
        handleGeofence(state.geofenceLocation)
    }

    private fun handleGeofence(geofenceLocation: LatLng?) {
        if (geofenceLocation != null) {
            MapUtil.setCircle(requireContext(), map!!, geofenceLocation)
        }
    }

    private fun handleCloseIcon(closeIconVisible: Boolean) = with(binding) {
        val imageDrawable = if (closeIconVisible) {
            R.drawable.ic_close
        } else {
            R.drawable.ic_search
        }

        imageViewSearch.apply {
            setImageResource(imageDrawable)
            setOnClickListener {
                if (closeIconVisible) {
                    viewModel.clearSearch()
                    editTextSearch.clearFocus()
                    editTextSearch.text?.clear()
                } else {
                    viewModel.search(editTextSearch.text.toString())
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun handleMarkerLocation(location: LatLng?) {
        if (location != null) {
            MapUtil.setMarker(requireContext(), map!!, location)
        }

        if (location == null && binding.cardViewAlarm.isVisible) {
            binding.cardViewAlarm.isVisible = false
            binding.cardViewAlarm
                .animate()
                .alpha(0f)
                .start()
            return
        }

        if (location != null && !binding.cardViewAlarm.isVisible) {
            binding.cardViewAlarm.isVisible = true
            binding.cardViewAlarm
                .animate()
                .alpha(1f)
                .start()
        }
    }

    private fun handleCameraLocation(location: LatLng) {
        MapUtil.goToLocation(map!!, location)
    }

    private fun handleSearchPredictions(isLoading: Boolean, prediction: List<SearchPrediction>) {
        binding.progressBarLoading.isVisible = isLoading
        binding.recyclerView.searchAdapter.submitList(prediction)
    }

    private fun requestPermissions() {
        val groupedPermissions = mutableListOf<String>()
        groupedPermissions.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        groupedPermissions.add(android.Manifest.permission.ACCESS_COARSE_LOCATION)

        groupPermissionRequest.launch(groupedPermissions.toTypedArray())

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            singlePermissionRequest.launch(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            singlePermissionRequest.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

    }


}