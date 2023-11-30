package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.CardAdapter
import com.example.finalproject_chilicare.data.response.CardArtikelResponse
import com.example.finalproject_chilicare.adapter.TabAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.TabResponse
import com.example.finalproject_chilicare.utils.DummyDataArtikel
import kotlinx.coroutines.launch


class ArticleActivity : AppCompatActivity() {
    private lateinit var rvTabArticle: RecyclerView
    private lateinit var rvCardArticle: RecyclerView

    private lateinit var tabResponses: ArrayList<TabResponse>
    private var cardArtikelResponse =  mutableListOf<CardArtikelResponse>()

    lateinit var etCariArtikel: EditText
    lateinit var cardAdapter: CardAdapter
    lateinit var ivBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        etCariArtikel = findViewById(R.id.etCariArtikel)
        ivBack = findViewById(R.id.ivBack)

        ivBack.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }

//        // RecyclerView untuk tab
//        rvTabArticle = findViewById(R.id.rv_tabArticle)
//        rvTabArticle.layoutManager =
//            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        rvTabArticle.setHasFixedSize(true)
//        tabResponses = ArrayList()
//        tabResponses.addAll(DummyDataArtikel.getDummyTabResponses())
//        // Menetapkan adapter TabAdapter untuk RecyclerView tab
//        rvTabArticle.adapter = TabAdapter(tabResponses)

        // RecyclerView untuk card
        rvCardArticle = findViewById(R.id.rv_cardArticle)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(cardArtikelResponse)
        rvCardArticle.adapter = cardAdapter


        lifecycleScope.launch {
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllArtikel()
            result.data.map {
                Log.d("debug", "hasilnya : ${it}")
                cardArtikelResponse.add(it)
            }

            // update recyclerviewnya
            cardAdapter.notifyDataSetChanged()
        }



    }




}
