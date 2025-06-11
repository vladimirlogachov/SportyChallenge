package com.sporty.weather.domain.model

internal data class Forecast(
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>,
) {
    fun isNotEmpty() = hourly.isNotEmpty() && daily.isNotEmpty()
}

internal data class HourlyForecast(
    val dateTime: Long,
    val temperature: Temperature,
    val weather: Weather,
    val cloudiness: Int,
    val wind: Wind,
    val visibility: Int,
    val precipitationProbability: String,
    val rainVolume: Float?,
    val snowVolume: Float?,
    val partOfDay: PartOfDay,
    val readableTime: String
)

internal data class DailyForecast(
    val readableDate: String,
    val tempHigh: Float,
    val tempLow: Float,
    val weather: Weather,
    val precipitationProbability: String,
)

internal data class Temperature(
    val actual: Float,
    val feelsLike: Float,
    val min: Float,
    val max: Float,
    val pressure: Int,
    val humidity: Int
)

internal data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

internal data class Wind(
    val speed: Float,
    val direction: Int,
    val gust: Float?
)

internal enum class PartOfDay() {
    DAY, NIGHT,
}
