package com.example.finalproject_chilicare.ui.home.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class WeatherMalangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_malang)

        val btnBackInWeather: ImageView = findViewById(R.id.btnBackInWeather)
        btnBackInWeather.setOnClickListener {
            finish()
        }
    }
}