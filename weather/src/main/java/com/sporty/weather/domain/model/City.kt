package com.sporty.weather.domain.model

internal data class City(
    val name: String,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val country: String = "",
)
