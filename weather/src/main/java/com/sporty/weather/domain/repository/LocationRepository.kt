package com.sporty.weather.domain.repository

import com.sporty.weather.domain.model.City

internal interface LocationRepository {

    suspend fun currentCity(): City

}
