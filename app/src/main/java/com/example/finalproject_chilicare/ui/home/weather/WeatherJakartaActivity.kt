package com.example.finalproject_chilicare.ui.home.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class WeatherJakartaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_jakarta)

        val btnSeeMoreWeather : Button = findViewById(R.id.btnselengkapnyajkt)

        val btnBackInWeather: ImageView = findViewById(R.id.btnBackInWeather)
        btnBackInWeather.setOnClickListener {
            finish()
        }

        btnSeeMoreWeather.setOnClickListener { Intent(this,WeatherJakartaContentActivity::class.java).also {
            startActivity(it)
        } }
    }
}