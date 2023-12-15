package com.example.finalproject_chilicare.ui.home.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.ui.home.WeatherActivity

class WeatherChooseCityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_choose_city)

        //Kembali ke HomeActivity
        val Kembali = findViewById<ImageView>(R.id.TombolKembali)
        Kembali.setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        val city1 = findViewById<CardView>(R.id.cv_1)
        city1.setOnClickListener {
            // Open WeatherJakartaActivity
            startActivity(Intent(this, WeatherJakartaActivity::class.java))
        }

        val city2 = findViewById<CardView>(R.id.cv_2)
        city2.setOnClickListener {
            // Open WeatherSurabayaActivity
            startActivity(Intent(this, WeatherSurabayaActivity::class.java))
        }

        val city3 = findViewById<CardView>(R.id.cv_3)
        city3.setOnClickListener {
            // Open WeatherMalangActivity
            startActivity(Intent(this, WeatherMalangActivity::class.java))
        }
    }
}