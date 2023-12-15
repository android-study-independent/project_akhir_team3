package com.example.finalproject_chilicare.ui.home.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class WeatherJakartaActivity : AppCompatActivity() {

    lateinit var btnSeeMoreWeather : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_jakarta)

        btnSeeMoreWeather = findViewById(R.id.btnSelengkapnyaJkt)

        val btnBackInWeather: ImageView = findViewById(R.id.btnBackInWeatherDetailJakarta)
        btnBackInWeather.setOnClickListener {
            startActivity(Intent(this, WeatherChooseCityActivity::class.java))
        }

        btnSeeMoreWeather.setOnClickListener { Intent(this,WeatherJakartaContentActivity::class.java).also {
            startActivity(it)
        } }


    }
}