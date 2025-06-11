package com.sporty.weather.data.repository

import android.Manifest
import androidx.annotation.RequiresPermission
import com.sporty.weather.data.scheme.toDomain
import com.sporty.weather.data.service.LocationService
import com.sporty.weather.data.service.OpenWeatherService
import com.sporty.weather.domain.model.City
import com.sporty.weather.domain.repository.LocationRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class LocationRepositoryImpl(
    private val locationService: LocationService,
    private val weathersMapService: OpenWeatherService,
    private val ioDispatcher: CoroutineContext,
) : LocationRepository {

    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ],
    )
    override suspend fun currentCity(): City = withContext(context = ioDispatcher) {
        with(receiver = locationService.currentLocation()) {
            weathersMapService.fetchCityNameByLocation(lat = latitude, lng = longitude)
                .toDomain()
        }
    }

}
