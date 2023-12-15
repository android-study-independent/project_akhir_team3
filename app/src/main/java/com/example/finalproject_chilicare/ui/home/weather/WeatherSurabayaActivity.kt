package com.example.finalproject_chilicare.ui.home.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class WeatherSurabayaActivity : AppCompatActivity() {
    lateinit var btnCuacaDetailSurabaya : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_surabaya)

        val btnBackInWeather: ImageView = findViewById(R.id.btnBackInWeatherDetailSurabaya)
        btnBackInWeather.setOnClickListener {
            startActivity(Intent(this, WeatherChooseCityActivity::class.java))
        }

        btnCuacaDetailSurabaya = findViewById(R.id.buttonLihatSelengkapnyaSurabaya)

        btnCuacaDetailSurabaya.setOnClickListener {
            startActivity(Intent(this, WeatherSurabayaContentActivity::class.java))
        }
    }
}