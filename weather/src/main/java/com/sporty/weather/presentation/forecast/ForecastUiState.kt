package com.sporty.weather.presentation.forecast

import com.sporty.weather.domain.model.City
import com.sporty.weather.domain.model.Forecast

internal data class ForecastUiState(
    val showProgress: Boolean = false,
    val permissionGranted: Boolean = false,
    val city: City = City(name = ""),
    val forecast: Forecast = Forecast(hourly = emptyList(), daily = emptyList()),
    val error: String? = null,
)
