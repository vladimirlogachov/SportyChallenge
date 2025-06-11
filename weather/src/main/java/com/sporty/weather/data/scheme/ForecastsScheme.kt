package com.sporty.weather.data.scheme

import com.sporty.weather.domain.model.DailyForecast
import com.sporty.weather.domain.model.Forecast
import com.sporty.weather.domain.model.HourlyForecast
import com.sporty.weather.domain.model.PartOfDay
import com.sporty.weather.domain.model.Temperature
import com.sporty.weather.domain.model.Weather
import com.sporty.weather.domain.model.Wind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
internal data class ForecastScheme(
    val list: List<ForecastItemScheme>,
)

@Serializable
internal data class ForecastItemScheme(
    val dt: Long,
    val main: MainWeatherScheme,
    val weather: List<WeatherScheme>,
    val clouds: CloudsScheme,
    val wind: WindScheme,
    val visibility: Int,
    val pop: Float,
    val rain: RainScheme? = null,
    val snow: SnowScheme? = null,
    val sys: ForecastSysScheme,
    @SerialName("dt_txt") val dateText: String,
)

@Serializable
internal data class MainWeatherScheme(
    val temp: Float,
    @SerialName("feels_like") val feelsLike: Float,
    @SerialName("temp_min") val tempMin: Float,
    @SerialName("temp_max") val tempMax: Float,
    val pressure: Int,
    @SerialName("sea_level") val seaLevel: Int,
    @SerialName("grnd_level") val groundLevel: Int,
    val humidity: Int,
    @SerialName("temp_kf") val tempKf: Float,
)

@Serializable
internal data class WeatherScheme(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
internal data class CloudsScheme(
    val all: Int
)

@Serializable
internal data class WindScheme(
    val speed: Float,
    val deg: Int,
    val gust: Float? = null,
)

@Serializable
internal data class RainScheme(
    @SerialName("3h") val volume: Float? = null,
)

@Serializable
internal data class SnowScheme(
    @SerialName("3h") val volume: Float? = null,
)

@Serializable
internal data class ForecastSysScheme(
    val pod: String, // "d" or "n"
)

/*
 * Ideally mappers should be moved to separate file(s), but for the sake of simplicity here
 * they are in the same file.
 */

internal fun ForecastScheme.toDomain(): Forecast = Forecast(
    hourly = list.take(n = 8).map(ForecastItemScheme::toDomain),
    daily = list.groupBy { item -> item.dateText.split(" ").first() }
        .map { (date, items) ->
            val high = items.maxBy { item -> item.main.tempMax }
            val low = items.minBy { item -> item.main.tempMin }
            val tempAverage = items.map { item -> item.main.temp }.average()
            val average = items.minBy { item -> abs(item.main.temp - tempAverage) }

            DailyForecast(
                readableDate = date.split("-").drop(n = 1).joinToString("/"),
                tempHigh = high.main.tempMax,
                tempLow = low.main.tempMin,
                weather = average.weather.first().toDomain(),
                precipitationProbability = average.pop.toReadablePrecipitationProbability(),
            )
        }.sortedBy { item -> item.readableDate },
)

internal fun ForecastItemScheme.toDomain(): HourlyForecast = HourlyForecast(
    dateTime = dt,
    temperature = main.toDomain(),
    weather = weather.first().toDomain(),
    cloudiness = clouds.all,
    wind = wind.toDomain(),
    visibility = visibility,
    precipitationProbability = pop.toReadablePrecipitationProbability(),
    rainVolume = rain?.volume,
    snowVolume = snow?.volume,
    partOfDay = sys.pod.toPartOfDay(),
    readableTime = dateText.split(" ").last().split(":").dropLast(1).joinToString(":"),
)

private fun Float.toReadablePrecipitationProbability(): String =
    takeIf { value -> value > 0f }
        ?.let { value ->  "${(value * 100).toInt()}%" }
        .orEmpty()

internal fun MainWeatherScheme.toDomain(): Temperature = Temperature(
    actual = temp,
    feelsLike = feelsLike,
    min = tempMin,
    max = tempMax,
    pressure = pressure,
    humidity = humidity,
)

internal fun WeatherScheme.toDomain(): Weather = Weather(
    id = id,
    main = main,
    description = description.replaceFirstChar { ch -> ch.uppercase() },
    icon = "https://openweathermap.org/img/wn/$icon@2x.png",
)

internal fun WindScheme.toDomain(): Wind = Wind(
    speed = speed,
    direction = deg,
    gust = gust,
)

internal fun String.toPartOfDay(): PartOfDay = when (this.lowercase()) {
    "d" -> PartOfDay.DAY
    "n" -> PartOfDay.NIGHT
    else -> PartOfDay.DAY
}
