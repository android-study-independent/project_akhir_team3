package com.example.finalproject_chilicare.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.PopularCityAdapter
import com.example.finalproject_chilicare.data.response.PopularCityResponse

class SearchPopularCityActivity : AppCompatActivity() {

    private lateinit var rv_CariKota: RecyclerView

    private lateinit var dataKota : ArrayList<PopularCityResponse>

    lateinit var titleList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_popular_city)

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

        rv_CariKota = findViewById(R.id.rv_CariKota)
        rv_CariKota.layoutManager = GridLayoutManager(this, 4)
        rv_CariKota.setHasFixedSize(true)

        dataKota = ArrayList()
        getData()
    }

    private fun getData() {
        for (i in titleList.indices) {
            val data = PopularCityResponse(titleList[i])
            dataKota.add(data)
        }
        rv_CariKota.adapter = PopularCityAdapter(dataKota)
    }
}