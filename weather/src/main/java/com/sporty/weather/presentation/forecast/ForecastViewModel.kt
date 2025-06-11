package com.sporty.weather.presentation.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sporty.weather.domain.repository.LocationRepository
import com.sporty.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
 * Using repositories directly here for the sake of simplicity, ideally should be replaced
 * with domain layer use cases or interactors.
 */
internal class ForecastViewModel(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(value = ForecastUiState())

    /*
     * We can actually convert it to read-only one with `asStateFlow()`, but again for the sake of
     * simplicity here I use simple type cast instead.
     */
    val uiState: StateFlow<ForecastUiState> = _uiState

    fun onPermissionGranted() {
        viewModelScope.launch {
            _uiState.update { current ->
                if (!current.permissionGranted) fetchCurrentLocation()
                current.copy(permissionGranted = true)
            }
        }
    }

    private fun fetchCurrentLocation() {
        viewModelScope.launch {
            runCatching {
                _uiState.update { current -> current.copy(showProgress = true) }
                locationRepository.currentCity()
            }.onSuccess { city ->
                _uiState.update { current -> current.copy(showProgress = false, city = city) }
            }.onFailure { error ->
                _uiState.update { current ->
                    current.copy(
                        showProgress = false,
                        error = error.message ?: "Unknown error" // ideally should be localised
                    )
                }
            }
        }.invokeOnCompletion {
            onFetchForecast()
        }
    }

    fun onQueryChange(query: String) {
        viewModelScope.launch {
            _uiState.update { current -> current.copy(city = current.city.copy(name = query)) }
        }
    }

    fun onFetchForecast() {
        viewModelScope.launch {
            runCatching {
                _uiState.update { current -> current.copy(showProgress = true) }

                weatherRepository.fetchForecast(
                    city = requireNotNull(
                        value = _uiState.value.city.takeIf { city -> city.name.isNotBlank() },
                    ) { "City name cannot be blank" } // ideally should be localised
                )
            }.onSuccess { forecast ->
                _uiState.update { current ->
                    current.copy(showProgress = false, forecast = forecast)
                }
            }.onFailure { error ->
                _uiState.update { current ->
                    current.copy(
                        showProgress = false,
                        error = error.message ?: "Unknown error" // ideally should be localised
                    )
                }
            }
        }
    }

}
