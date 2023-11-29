package com.example.finalproject_chilicare.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.PopularCityAdapter
import com.example.finalproject_chilicare.data.response.PopularCityResponse

class SearchPopularCityActivity : AppCompatActivity() {

    private lateinit var rv_CariKota: RecyclerView
    private lateinit var btnOke: Button

    private lateinit var dataKota: ArrayList<PopularCityResponse>

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
        btnOke = findViewById(R.id.btnOke)
        rv_CariKota.layoutManager = GridLayoutManager(this, 4)
        rv_CariKota.setHasFixedSize(true)


        btnOke.setOnClickListener {
            val intent = Intent()
            intent.putExtra("selectedCity", "Semarang")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

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