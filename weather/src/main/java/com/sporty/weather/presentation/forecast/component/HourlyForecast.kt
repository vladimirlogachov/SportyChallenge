package com.sporty.weather.presentation.forecast.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.sporty.weather.R
import com.sporty.weather.domain.model.HourlyForecast
import com.sporty.weather.domain.model.PartOfDay
import com.sporty.weather.domain.model.Temperature
import com.sporty.weather.domain.model.Weather
import com.sporty.weather.domain.model.Wind
import kotlin.math.roundToInt

@Composable
internal fun HourlyForecast(
    items: List<HourlyForecast>,
    modifier: Modifier = Modifier,
) = Card(
    modifier = modifier,
    shape = MaterialTheme.shapes.extraLarge,
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        text = stringResource(R.string.hourly_forecast),
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        contentPadding = PaddingValues(all = 16.dp),
    ) {
        items(items = items, key = { item -> item.dateTime }) { item ->
            ForecastItem(item = item)
        }
    }
}

@Composable
private fun ForecastItem(
    item: HourlyForecast,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(space = 4.dp),
) {
    Text(
        text = "${item.temperature.actual.roundToInt()}°",
        style = MaterialTheme.typography.bodyMedium
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(size = 48.dp),
            painter = rememberAsyncImagePainter(
                model = item.weather.icon,
                contentScale = ContentScale.FillBounds,
                error = rememberVectorPainter(image = Icons.Default.Warning),
            ),
            contentDescription = item.weather.description,
        )
        Text(
            text = item.precipitationProbability,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
    Text(
        text = item.readableTime,
        style = MaterialTheme.typography.bodyMedium
    )

}

@[Composable Preview(showBackground = true)]
private fun HourlyForecastPreview() {
    MaterialTheme {
        HourlyForecast(
            modifier = Modifier.padding(all = 16.dp),
            items = hourlyForecast,
        )
    }
}

internal val hourlyForecast = listOf(
    HourlyForecast(
        dateTime = 1,
        temperature = Temperature(293.15f, 293.15f, 292.0f, 295.0f, 1012, 60),
        weather = Weather(800, "Clear", "clear sky", "01d"),
        cloudiness = 0,
        wind = Wind(3.5f, 120, 4.2f),
        visibility = 10000,
        precipitationProbability = "",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.DAY,
        readableTime = "00:00"
    ),
    HourlyForecast(
        dateTime = 2,
        temperature = Temperature(296.0f, 296.5f, 295.0f, 297.0f, 1015, 50),
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        cloudiness = 20,
        wind = Wind(4.0f, 130, 4.8f),
        visibility = 10000,
        precipitationProbability = "10%",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.DAY,
        readableTime = "03:00"
    ),
    HourlyForecast(
        dateTime = 3,
        temperature = Temperature(294.0f, 293.5f, 293.0f, 294.0f, 1013, 70),
        weather = Weather(802, "Clouds", "scattered clouds", "03n"),
        cloudiness = 40,
        wind = Wind(2.8f, 100, 3.1f),
        visibility = 10000,
        precipitationProbability = "",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.NIGHT,
        readableTime = "06:00"
    ),
    HourlyForecast(
        dateTime = 4,
        temperature = Temperature(294.0f, 293.5f, 293.0f, 294.0f, 1013, 70),
        weather = Weather(802, "Clouds", "scattered clouds", "03n"),
        cloudiness = 40,
        wind = Wind(2.8f, 100, 3.1f),
        visibility = 10000,
        precipitationProbability = "",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.NIGHT,
        readableTime = "09:00"
    ),
    HourlyForecast(
        dateTime = 5,
        temperature = Temperature(294.0f, 293.5f, 293.0f, 294.0f, 1013, 70),
        weather = Weather(802, "Clouds", "scattered clouds", "03n"),
        cloudiness = 40,
        wind = Wind(2.8f, 100, 3.1f),
        visibility = 10000,
        precipitationProbability = "",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.NIGHT,
        readableTime = "12:00"
    ),
    HourlyForecast(
        dateTime = 6,
        temperature = Temperature(294.0f, 293.5f, 293.0f, 294.0f, 1013, 70),
        weather = Weather(802, "Clouds", "scattered clouds", "03n"),
        cloudiness = 40,
        wind = Wind(2.8f, 100, 3.1f),
        visibility = 10000,
        precipitationProbability = "",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.NIGHT,
        readableTime = "15:00"
    ),
    HourlyForecast(
        dateTime = 7,
        temperature = Temperature(294.0f, 293.5f, 293.0f, 294.0f, 1013, 70),
        weather = Weather(802, "Clouds", "scattered clouds", "03n"),
        cloudiness = 40,
        wind = Wind(2.8f, 100, 3.1f),
        visibility = 10000,
        precipitationProbability = "",
        rainVolume = null,
        snowVolume = null,
        partOfDay = PartOfDay.NIGHT,
        readableTime = "18:00"
    )
)
