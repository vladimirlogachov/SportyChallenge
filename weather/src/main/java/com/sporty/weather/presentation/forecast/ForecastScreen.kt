package com.sporty.weather.presentation.forecast

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sporty.weather.R
import com.sporty.weather.domain.model.City
import com.sporty.weather.domain.model.Forecast
import com.sporty.weather.presentation.forecast.component.DailyForecast
import com.sporty.weather.presentation.forecast.component.HourlyForecast
import com.sporty.weather.presentation.forecast.component.dailyForecast
import com.sporty.weather.presentation.forecast.component.hourlyForecast
import com.sporty.weather.presentation.persmission.PermissionBox
import org.koin.compose.viewmodel.koinViewModel

internal object ForecastScreen {

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        permissions: List<String> = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    ) {
        val viewModel = koinViewModel<ForecastViewModel>()
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        if (!state.permissionGranted) {
            PermissionBox(
                permissions = permissions,
                requiredPermissions = listOf(permissions.first()),
                onGranted = { viewModel.onPermissionGranted() }
            )
        } else {
            ForecastScreen(
                modifier = modifier,
                state = state,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::onFetchForecast,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ForecastScreen(
    state: ForecastUiState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = Scaffold(
    modifier = modifier,
    topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = stringResource(id = R.string.forecast)) }
        )
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
) { paddingValues ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
    ) {
        AnimatedVisibility(visible = state.showProgress) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            query = state.city.name,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
        )
        if (state.forecast.isNotEmpty()) {
            HourlyForecast(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = state.forecast.hourly,
            )
            DailyForecast(
                modifier = Modifier.padding(horizontal = 16.dp),
                items = state.forecast.daily,
            )
        }
    }

    LaunchedEffect(state) {
        if (state.error.isNullOrBlank().not()) {
            snackbarHostState.showSnackbar(message = state.error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier,
) = SearchBar(
    modifier = modifier,
    expanded = false,
    onExpandedChange = { /* no ops */ },
    inputField = {
        SearchBarDefaults.InputField(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = { onSearch() },
            expanded = false,
            onExpandedChange = { /* no ops */ },
            placeholder = { Text(text = stringResource(R.string.city)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search),
                )
            },
            trailingIcon = {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear),
                    )
                }
            },
        )
    },
    windowInsets = WindowInsets.ime,
    content = { /* no content */ },
)

@[Composable Preview]
private fun ForecastScreenPreview() {
    MaterialTheme {
        ForecastScreen(
            state = ForecastUiState(
                showProgress = false,
                city = City(name = "Málaga"),
                forecast = fakeForecast,
            ),
            onQueryChange = {},
            onSearch = {},
        )
    }
}

private val fakeForecast = Forecast(
    hourly = hourlyForecast,
    daily = dailyForecast,
)
