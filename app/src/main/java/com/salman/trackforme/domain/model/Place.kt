package com.salman.trackforme.domain.model

/**
* Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/9/2023.
*/
data class Place(
    val id: String,
    val name: String?,
    val address: String,
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        const val EMPTY_ID = ""
        const val EMPTY_NAME = ""
        const val EMPTY_ADDRESS = ""
        const val EMPTY_LATITUDE = 0.0
        const val EMPTY_LONGITUDE = 0.0
    }
}
