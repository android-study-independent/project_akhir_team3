package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.CardAdapter
import com.example.finalproject_chilicare.data.response.CardResponse
import com.example.finalproject_chilicare.adapter.TabAdapter
import com.example.finalproject_chilicare.data.response.TabResponse
import com.example.finalproject_chilicare.ui.home.fragment.HomeFragment
import com.example.finalproject_chilicare.utils.DummyDataArtikel

class ArticleActivity : AppCompatActivity() {
    private lateinit var rvTabArticle: RecyclerView
    private lateinit var rvCardArticle: RecyclerView

    private lateinit var tabResponses: ArrayList<TabResponse>
    private lateinit var cardResponses: ArrayList<CardResponse>

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

        // RecyclerView untuk tab
        rvTabArticle = findViewById(R.id.rv_tabArticle)
        rvTabArticle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTabArticle.setHasFixedSize(true)

        // RecyclerView untuk card
        rvCardArticle = findViewById(R.id.rv_cardArticle)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.setHasFixedSize(true)

        tabResponses = ArrayList()
        cardResponses = arrayListOf<CardResponse>()

        // Menggunakan data dummy dari DummyDataUtil
        tabResponses.addAll(DummyDataArtikel.getDummyTabResponses())
        cardResponses.addAll(DummyDataArtikel.getDummyCardResponses())

        // Menetapkan adapter TabAdapter untuk RecyclerView tab
        rvTabArticle.adapter = TabAdapter(tabResponses)

        // Inisialisasi dan atur adapter CardAdapter untuk RecyclerView card
        cardAdapter = CardAdapter(cardResponses)
        rvCardArticle.adapter = cardAdapter

        // Menangani klik item di RecyclerView card
        cardAdapter.onItemClick = {
            Log.d("ArticleActivity", "Clicked item: $it")
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }

        // Menambahkan TextWatcher untuk memfilter data saat EditText berubah
        etCariArtikel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //
            }
        })
    }

    // Fungsi untuk memfilter data di RecyclerView card
    private fun filterList(query: String?) {
        cardAdapter.filter.filter(query)
    }

}
