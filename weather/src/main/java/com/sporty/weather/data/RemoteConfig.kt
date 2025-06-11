package com.sporty.weather.data

/*
 * Ideally host and key should not be stored as plain strings like this, instead we
 * should provide them as build-time constants using CI env variables.
 */
internal object RemoteConfig {

    const val WEATHER_MAP_API_HOST = "api.openweathermap.org"
    const val WEATHER_MAP_API_KEY = "ed8abc651f7c11838c6cdc72ba17f6e5"

}
