package com.sporty.weather.data.scheme

import com.sporty.weather.domain.model.City
import kotlinx.serialization.Serializable

@Serializable
internal data class GeoLocationScheme(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
)

/*
 * Ideally mappers should be moved to separate file(s), but for the sake of simplicity here
 * they are in the same file.
 */

internal fun GeoLocationScheme.toDomain(): City =
    City(
        name = name,
        lat = lat,
        lon = this@toDomain.lon,
        country = country,
    )
