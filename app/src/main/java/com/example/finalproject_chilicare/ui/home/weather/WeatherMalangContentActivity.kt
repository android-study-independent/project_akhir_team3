package com.example.finalproject_chilicare.ui.home.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class WeatherMalangContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_malang_content)

        val btnBackInWeather: ImageView = findViewById(R.id.btnBackInWeather)
        btnBackInWeather.setOnClickListener {
            val intent = Intent(this, WeatherMalangActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}