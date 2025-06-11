package com.sporty.weather.domain.repository

import com.sporty.weather.domain.model.City
import com.sporty.weather.domain.model.Forecast

internal interface WeatherRepository {

    suspend fun fetchForecast(city: City): Forecast

}
