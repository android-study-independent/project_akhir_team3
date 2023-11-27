package com.example.finalproject_chilicare.data.models



data class CurrentWeather(
    val currentWeather: CurrentWeatherDetails,
    val forecast: List<Forecast>,
    val hourlyweather: List<Hourlyweather>
)