package com.example.finalproject_chilicare.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.PopularCityAdapter
import com.example.finalproject_chilicare.data.models.CurrentWeatherDetails

class SearchPopularCityActivity : AppCompatActivity() {

    private lateinit var adapter:PopularCityAdapter
    private var listUpcoming = mutableListOf<CurrentWeatherDetails>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_popular_city)

        val rvPopularCity = findViewById<RecyclerView>(R.id.rvPopularCity)
        rvPopularCity.layoutManager = LinearLayoutManager(this)
        adapter = PopularCityAdapter(listUpcoming)
        rvPopularCity.adapter = adapter


    }


}