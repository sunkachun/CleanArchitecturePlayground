package com.example.cleanarchitectureplayground.ui.screen.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleanarchitectureplayground.R
import com.example.domain.weather.weatherforecast.model.WeatherStatus
import com.example.presentation.weather.weatherforecast.model.NineDayWeatherForecastSection
import com.example.presentation.weather.weatherforecast.model.WeatherForecastDisplay
import com.example.presentation.weatherforecast.WeatherForecastViewModel

@Composable
fun WeatherForecastScreen(
    navController: NavController,
    modifier: Modifier,
) {
    val viewModel: WeatherForecastViewModel = hiltViewModel()

    val weatherState = viewModel.nineDayWeatherForecastFlow.collectAsState().value

    WeatherForecastContent(
        weatherForecast = weatherState,
        onBackClick = { navController.popBackStack() }
    )
}

@Composable
fun WeatherForecastContent(
    weatherForecast: NineDayWeatherForecastSection?,
    onBackClick: () -> Unit
) {
    Scaffold(

    ) { paddingValues ->
        weatherForecast?.let { forecast ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = forecast.generalDisplay,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Updated: ${forecast.updateTime}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                items(forecast.weatherForecast) { weatherItem ->
                    WeatherForecastItem(weatherItem)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun WeatherForecastItem(forecast: WeatherForecastDisplay) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = forecast.forecastIcon.icon),
                contentDescription = forecast.forecastDisplayWeather,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = forecast.forecastDisplayTitle,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = forecast.forecastDisplayWeather,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Max: ${forecast.forecastDisplayMaxTemp}°C",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Min: ${forecast.forecastDisplayMinTemp}°C",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

val WeatherStatus.icon: Int
    get() = when (this) {
        WeatherStatus.SUNNY -> R.drawable.ic_dashboard_button_sunny
        WeatherStatus.CLOUDY -> R.drawable.ic_weather_cloudly
        WeatherStatus.THUNDERSTORMS -> R.drawable.ic_weather_thunderstorm
        WeatherStatus.WINDY -> R.drawable.ic_weather_storm
        else -> R.drawable.ic_weather_cloudly
    }