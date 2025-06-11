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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.sporty.weather.domain.model.DailyForecast
import com.sporty.weather.domain.model.Weather
import kotlin.math.roundToInt

@Composable
internal fun DailyForecast(
    items: List<DailyForecast>,
    modifier: Modifier = Modifier,
) = Card(
    modifier = modifier,
    shape = MaterialTheme.shapes.extraLarge,
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        text = stringResource(R.string.daily_forecast),
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        contentPadding = PaddingValues(all = 16.dp),
    ) {
        items(items = items, key = { item -> item.readableDate }) { item ->
            ForecastItem(item = item)
        }
    }
}

@Composable
private fun ForecastItem(
    item: DailyForecast,
    modifier: Modifier = Modifier,
) = Card(
    modifier = modifier,
    shape = CircleShape,
    colors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ),
) {
    Column(
        modifier = Modifier.padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 4.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${item.tempHigh.roundToInt()}°",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${item.tempLow.roundToInt()}°",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
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
            text = item.readableDate,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@[Composable Preview(showBackground = true)]
private fun DailyForecastPreview() {
    MaterialTheme {
        DailyForecast(
            modifier = Modifier.padding(all = 16.dp),
            items = dailyForecast,
        )
    }
}

internal val dailyForecast = listOf(
    DailyForecast(
        readableDate = "Today",
        tempHigh = 293.15f,
        tempLow = 292.0f,
        weather = Weather(800, "Clear", "clear sky", "01d"),
        precipitationProbability = "",
    ),
    DailyForecast(
        readableDate = "06/11",
        tempHigh = 295.0f,
        tempLow = 294.0f,
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        precipitationProbability = "10%",
    ),
    DailyForecast(
        readableDate = "06/12",
        tempHigh = 295.0f,
        tempLow = 294.0f,
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        precipitationProbability = "10%",
    ),
    DailyForecast(
        readableDate = "06/13",
        tempHigh = 295.0f,
        tempLow = 294.0f,
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        precipitationProbability = "10%",
    ),
    DailyForecast(
        readableDate = "06/14",
        tempHigh = 295.0f,
        tempLow = 294.0f,
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        precipitationProbability = "10%",
    ),
    DailyForecast(
        readableDate = "06/15",
        tempHigh = 296.0f,
        tempLow = 294.0f,
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        precipitationProbability = "10%",
    ),
    DailyForecast(
        readableDate = "06/16",
        tempHigh = 296.0f,
        tempLow = 294.0f,
        weather = Weather(801, "Clouds", "few clouds", "02d"),
        precipitationProbability = "10%",
    ),
)
