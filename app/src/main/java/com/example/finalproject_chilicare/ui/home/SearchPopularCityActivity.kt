package com.example.finalproject_chilicare.ui.home

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.PopularCityAdapter
import com.example.finalproject_chilicare.data.response.PopularCityResponse
import com.example.finalproject_chilicare.ui.home.weather.WeatherJakartaActivity

class SearchPopularCityActivity : AppCompatActivity(),PopularCityAdapter.OnItemClickListener {

    private lateinit var rv_CariKota: RecyclerView
    private lateinit var btnBack : ImageView

    private lateinit var dataKota : ArrayList<PopularCityResponse>

    lateinit var titleList: Array<String>
    lateinit var btnLocation : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_popular_city)

        btnBack = findViewById(R.id.iconBackInSearchPopular)

        btnBack.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

        titleList = arrayOf(
            "Semarang",
            "Surabaya",
            "Jakarta",
            "Yogyakarta",
            "Sleman",
            "Semarang",
            "Malang",
            "Kediri",
            "Surabaya",
            "Jakarta",
            "Yogyakarta",
            "Sleman",
            "Sidoarjo",
            "Tanggerang"
        )

        rv_CariKota = findViewById(R.id.rvCariKota)
        rv_CariKota.layoutManager = GridLayoutManager(this, 4)
        rv_CariKota.setHasFixedSize(true)


        dataKota = ArrayList()
        getData()

        rv_CariKota.adapter = PopularCityAdapter(dataKota,this)
    }

    override fun onItemClick(city: String) {
        // Handle item click event, for example, start a new activity with the selected city
        val intent = Intent(this, WeatherJakartaActivity::class.java)
        intent.putExtra("Jakarta", city)
        startActivity(intent)
    }

    private fun getData() {
        for (i in titleList.indices) {
            val data = PopularCityResponse(titleList[i])
            dataKota.add(data)
        }

    }


}