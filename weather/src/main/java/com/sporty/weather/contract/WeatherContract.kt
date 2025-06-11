package com.sporty.weather.contract

import androidx.compose.runtime.Composable
import com.sporty.weather.di.WeatherModule
import com.sporty.weather.presentation.forecast.ForecastScreen
import org.koin.core.module.Module

public object WeatherContract {

    public val module: Module = WeatherModule

    @Composable
    public fun Screen(): Unit = ForecastScreen.Content()

}
