package com.example.finalproject_chilicare.ui.home.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class WeatherMalangActivity : AppCompatActivity() {

    lateinit var btnDetailCuacaMalang : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_malang)

        val btnBackInWeather: ImageView = findViewById(R.id.btnBackInWeatherDetailMalang)
        btnBackInWeather.setOnClickListener {
            startActivity(Intent(this, WeatherChooseCityActivity::class.java))
        }

        btnDetailCuacaMalang = findViewById(R.id.buttonLihatSelengkapnyaMalang)

        btnDetailCuacaMalang.setOnClickListener {
            startActivity(Intent(this, WeatherMalangContentActivity::class.java))
        }
    }
}