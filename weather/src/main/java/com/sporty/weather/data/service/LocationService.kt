package com.sporty.weather.data.service

import android.Manifest
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.tasks.await

internal class LocationService(
    private val locationClient: FusedLocationProviderClient,
) {

    @RequiresPermission(
        anyOf = [
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ],
    )
    suspend fun currentLocation(): Location =
        locationClient.lastLocation.await()

}
