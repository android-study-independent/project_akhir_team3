package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import kotlinx.coroutines.launch

class MateriLMSActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var ivMore: ImageView
    lateinit var cdmateriadapter : CardLmsMateriAdapter
    lateinit var rvMateriLms : RecyclerView
    private var materilistlms = mutableListOf<ListMateriLMS>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi_lmsactivity)

        //inisasi layout xml
        ivBack = findViewById(R.id.btnBackMateriLms)

        //Inisiasi ketika back dipencet kembali ke halaman beranda LmsActivity
        ivBack.setOnClickListener {
            Intent(this, LmsFragment::class.java).also {
                startActivity(it)
            }
        }


        // card materi lms
        rvMateriLms = findViewById(R.id.rv_cardLms)
        rvMateriLms.layoutManager = LinearLayoutManager(this)
        cdmateriadapter = CardLmsMateriAdapter(materilistlms)
        rvMateriLms.adapter = cdmateriadapter

        //get API Lifecycle scope
        lifecycleScope.launch {
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllLms()
            result.data.map {
                Log.d("LMS","hasil ${it}")
//                materilistlms.add(it)
            }
        }

    }

}