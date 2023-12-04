package com.example.finalproject_chilicare.data.models

data class Forecast(
    val date: String,
    val temperature: Double,
    val weatherDescription: String,
    val icon : String
)