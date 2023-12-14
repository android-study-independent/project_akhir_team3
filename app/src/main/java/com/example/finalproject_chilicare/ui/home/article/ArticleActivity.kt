package com.example.finalproject_chilicare.ui.home.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.article.CardAdapter
import com.example.finalproject_chilicare.data.response.article.CardArtikelResponse
import com.example.finalproject_chilicare.adapter.article.TabAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.article.TabResponse
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.utils.OnTabClickListener
import kotlinx.coroutines.launch


class ArticleActivity : AppCompatActivity(), OnTabClickListener {

    lateinit var ivBack: ImageView
    lateinit var etCariArtikel: EditText
    lateinit var cardAdapter: CardAdapter
    private lateinit var rvTabArticle: RecyclerView
    private lateinit var rvCardArticle: RecyclerView
    private lateinit var tabResponses: ArrayList<TabResponse>
    private var cardArtikelResponse =  mutableListOf<CardArtikelResponse>()
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        // INISIASI DARI XML
        etCariArtikel = findViewById(R.id.etCariArtikel)
        ivBack = findViewById(R.id.ivBack)
        progressBar = findViewById(R.id.progressBarArtikel)

        // NAVIGATE BACK TKE HOME
        ivBack.setOnClickListener {
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
            }
        }

        // SEARCH BAR ETARTIKEL
        etCariArtikel.addTextChangedListener { text ->
            val query = text.toString().trim()

            if (query.isEmpty()) {
                cardAdapter.updateData(cardArtikelResponse)
            } else {
                cardAdapter.searchByCategory(query)
            }
        }

        // TAB ARTIKEL
        rvTabArticle = findViewById(R.id.rv_tabArticle)
        rvTabArticle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTabArticle.setHasFixedSize(true)
        tabResponses = ArrayList()
        rvTabArticle.adapter = TabAdapter(tabResponses, this)

        // CARD ARTIKEL
        rvCardArticle = findViewById(R.id.rv_cardArticle)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(cardArtikelResponse)
        rvCardArticle.adapter = cardAdapter

        // LIFECYCLE SCOPE
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllArtikel()
            result.data.map {
                Log.d("debug", "hasilnya : $it")
                cardArtikelResponse.add(it)
                progressBar.visibility = View.GONE
            }

            // RECYCLERVIEW TAB
            tabResponses.addAll(cardArtikelResponse
                .filter { it.category != null }
                .distinctBy { it.category }
                .map { TabResponse(it.category!!) })

            // UPDATE RECYCLERVIEW NYA
            cardAdapter.notifyDataSetChanged()
            rvTabArticle.adapter?.notifyDataSetChanged()
        }

        // UNTUK MENUJU KE DETAIL
        cardAdapter.onItemClick = {
            Log.d("ArticleActivity", "Clicked item: $it")
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("articles", it)
            intent.putParcelableArrayListExtra("articleList", ArrayList(cardArtikelResponse)) // ini untuk rv_cardArticle2
            startActivity(intent)
        }

    }

    // FUNGSI UNTUK KETIKA TAB ARTIKEL DI KLIK
    override fun onTabClick(category: String) {
        val filteredArticles = cardArtikelResponse.filter { it.category == category }

        cardAdapter.updateData(filteredArticles)
    }

}
