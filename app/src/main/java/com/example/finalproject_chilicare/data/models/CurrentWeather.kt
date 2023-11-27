package com.example.finalproject_chilicare.data.models

data class CurrentWeather(
    val city: String,
    val dewPoint: String,
    val humidity: Int,
    val status: String,
    val sunrise: String,
    val sunset: String,
    val temperature: Double,
    val weatherDescription: String,
    val windDirection: String,
    val windSpeed: String
)