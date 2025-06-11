package com.sporty.weather.di

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.sporty.weather.data.repository.LocationRepositoryImpl
import com.sporty.weather.data.repository.WeatherRepositoryImpl
import com.sporty.weather.data.service.LocationService
import com.sporty.weather.data.service.OpenWeatherService
import com.sporty.weather.domain.repository.LocationRepository
import com.sporty.weather.domain.repository.WeatherRepository
import com.sporty.weather.presentation.forecast.ForecastViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val WeatherModule: Module = module {

    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }

    factoryOf(::LocationService)
    factoryOf(::OpenWeatherService)

    single<LocationRepository> {
        LocationRepositoryImpl(
            weathersMapService = get(),
            locationService = get(),
            ioDispatcher = get(named("ioDispatcher")),
        )
    }

    single<WeatherRepository> {
        WeatherRepositoryImpl(
            service = get(),
            ioDispatcher = get(named("ioDispatcher")),
        )
    }

    viewModelOf(::ForecastViewModel)

}
