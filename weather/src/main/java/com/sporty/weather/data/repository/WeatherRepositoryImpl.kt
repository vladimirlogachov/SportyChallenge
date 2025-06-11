package com.sporty.weather.data.repository

import com.sporty.weather.data.scheme.toDomain
import com.sporty.weather.data.service.OpenWeatherService
import com.sporty.weather.domain.model.City
import com.sporty.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class WeatherRepositoryImpl(
    private val service: OpenWeatherService,
    private val ioDispatcher: CoroutineContext,
) : WeatherRepository {

    override suspend fun fetchForecast(city: City) = withContext(ioDispatcher) {
        service.fetchCityLocationByName(city = city)
            .toDomain()
            .let { city -> service.fetchForecast(city = city) }
            .toDomain()
    }

}
