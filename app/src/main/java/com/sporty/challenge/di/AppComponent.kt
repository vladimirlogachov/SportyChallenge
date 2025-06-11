package com.sporty.challenge.di

import com.sporty.weather.contract.WeatherContract

val appComponent = listOf(
    appModule,
    WeatherContract.module,
)
