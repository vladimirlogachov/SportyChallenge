package com.sporty.weather.data.service

import com.sporty.weather.data.RemoteConfig
import com.sporty.weather.data.scheme.ForecastScheme
import com.sporty.weather.data.scheme.GeoLocationScheme
import com.sporty.weather.domain.model.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url

internal class OpenWeatherService(
    private val httpClient: HttpClient
) {

    suspend fun fetchCityNameByLocation(lat: Double, lng: Double) = httpClient.get {
        url(
            scheme = "http",
            host = RemoteConfig.WEATHER_MAP_API_HOST,
            path = "/geo/1.0/reverse",
        ) {
            parameter(key = "lat", value = lat)
            parameter(key = "lon", value = lng)
            parameter(key = "limit", value = 1)
            parameter(key = "appid", value = RemoteConfig.WEATHER_MAP_API_KEY)
        }
    }.body<List<GeoLocationScheme>>().first()

    suspend fun fetchCityLocationByName(city: City) = httpClient.get {
        url(
            scheme = "http",
            host = RemoteConfig.WEATHER_MAP_API_HOST,
            path = "/geo/1.0/direct",
        ) {
            parameter(key = "q", value = city.name)
            parameter(key = "limit", value = 1)
            parameter(key = "appid", value = RemoteConfig.WEATHER_MAP_API_KEY)
        }
    }.body<List<GeoLocationScheme>>().first()

    suspend fun fetchForecast(city: City) = httpClient.get {
        url(
            scheme = "https",
            host = RemoteConfig.WEATHER_MAP_API_HOST,
            path = "/data/2.5/forecast",
        ) {
            parameter(key = "lat", value = city.lat)
            parameter(key = "lon", value = city.lon)
            parameter(key = "units", value = "metric")
            parameter(key = "appid", value = RemoteConfig.WEATHER_MAP_API_KEY)
        }
    }.body<ForecastScheme>()

}
