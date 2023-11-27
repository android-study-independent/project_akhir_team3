package com.example.finalproject_chilicare.data.response

import com.example.finalproject_chilicare.data.models.CurrentWeather
import com.example.finalproject_chilicare.data.models.Forecast
import com.example.finalproject_chilicare.data.models.Hourlyweather

data class GetWeatherResponse(
    val currentWeather: CurrentWeather,
    val forecast: List<Forecast>,
    val hourlyweather: List<Hourlyweather>
)