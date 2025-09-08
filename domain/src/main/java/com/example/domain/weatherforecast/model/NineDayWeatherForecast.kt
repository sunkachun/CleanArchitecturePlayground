package com.example.domain.weather.weatherforecast.model

import com.example.domain.weatherforecast.model.WeatherStatus

data class NineDayWeatherForecast(
    val generalSituation: String,
    val updateTime: String,
    val weatherForecast: List<WeatherForecast>,
)

data class WeatherForecast(
    val forecastDate: String,
    val week: String,
    val forecastWeather: String,
    val forecastMaxtemp: Int,
    val forecastMintemp: Int,
    val forecastIcon: WeatherStatus,
)
