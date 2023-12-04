package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.CardAdapter
import com.example.finalproject_chilicare.data.response.CardArtikelResponse
import com.example.finalproject_chilicare.utils.DummyDataArtikel
import com.squareup.picasso.Picasso

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var rvCardArticle: RecyclerView

    lateinit var cardAdapter: CardAdapter
    private var cardArtikelResponse = mutableListOf<CardArtikelResponse>()

    private lateinit var tvWebView: WebView
    private lateinit var ivKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)



        ivKembali = findViewById(R.id.ivKembali)
        tvWebView = findViewById(R.id.tvWebView)



        ivKembali.setOnClickListener {
            Intent(this, ArticleActivity::class.java).also {
                startActivity(it)
            }
        }


        val getData = intent.getParcelableArrayListExtra<CardArtikelResponse>("articles")
        if (getData != null && getData.isNotEmpty()) {
            val firstArticle = getData[0]

            // Menetapkan data ke view
            val category: TextView = findViewById(R.id.tvTab)
            val title: TextView = findViewById(R.id.tvJudulartikel)
            val readTime: TextView = findViewById(R.id.tv_KeteranganDibaca)
            val cover: ImageView = findViewById(R.id.ivArticle)
            val content: WebView = findViewById(R.id.tvWebView)
            val source: TextView = findViewById(R.id.tvSumberLink)

            // Menetapkan data ke view berdasarkan firstArticle
            category.text = firstArticle.category
            title.text = firstArticle.title
            readTime.text = firstArticle.readTime
            Picasso.get().load(firstArticle.coverPath).into(cover)
            firstArticle.content?.let { content.loadData(it, "text/html", "utf-8") }
            source.text = firstArticle.source

            // Memuat data HTML ke WebView
            content.settings.javaScriptEnabled = true
            content.webViewClient = WebViewClient()
            firstArticle.content?.let {
                content.loadDataWithBaseURL(
                    null,
                    it, "text/html", "UTF-8", null
                )
            }

            // Menambahkan styling pada WebView
            val styledContent = """
        <html>
        <head>
            <style>
                body {
                    font-family: @font/plusjakartasans_regular_400;
                    font-size: 16px;
                    color: #333333;
                    line-height: 1.6;
                }
                p {
                    text-align: justify;
                    margin-bottom: 10px;
                }
                ol {
                    padding-left: 20px;
                    margin-bottom: 10px;
                }
            </style>
        </head>
        <body>
            <p>${firstArticle.desc ?: ""}</p>
            ${firstArticle.content ?: ""}
        </body>
        </html>
    """.trimIndent()

            tvWebView.settings.javaScriptEnabled = true
            tvWebView.webViewClient = WebViewClient()
            tvWebView.loadDataWithBaseURL(null, styledContent, "text/html", "UTF-8", null)
        }
        Log.d("DetailArticleActivity", "Received data: $getData")



        Log.d("DetailArticleActivity", "Received data: $getData")

        rvCardArticle = findViewById(R.id.rv_cardArticle2)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardAdapter(cardArtikelResponse)
        rvCardArticle.adapter = cardAdapter

        val articleList = intent.getParcelableArrayListExtra<CardArtikelResponse>("articles")
        if (articleList != null) {
            cardArtikelResponse.addAll(articleList)
        }


    }


}
