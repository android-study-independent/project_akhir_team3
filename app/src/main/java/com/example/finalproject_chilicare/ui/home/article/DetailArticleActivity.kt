package com.example.finalproject_chilicare.ui.home.article

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.article.CardAdapter
import com.example.finalproject_chilicare.data.response.article.CardArtikelResponse
import com.squareup.picasso.Picasso
import kotlin.random.Random

class DetailArticleActivity : AppCompatActivity() {

    lateinit var cardAdapter: CardAdapter
    private lateinit var tvWebView: WebView
    private lateinit var ivKembali: ImageView
    private lateinit var rvCardArticle: RecyclerView
    private var cardArtikelResponse = mutableListOf<CardArtikelResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        // INISIASI DARI XML
        ivKembali = findViewById(R.id.ivKembali)
        tvWebView = findViewById(R.id.tvWebView)

        // NAVIGATE BACK TO ARTIKEL
        ivKembali.setOnClickListener {
            Intent(this, ArticleActivity::class.java).also {
                startActivity(it)
            }
        }

        // MENDAPATKAN DATA MENGGUNAKAN KATA KUNCI ARTICLES DARI HALAMAN ARTICLEACTIVITY
        val getData = intent.getParcelableExtra<CardArtikelResponse>("articles")

        // PENERAPAN UNTUK MENGAMBIL DATA NYA DARI HALAMAN ARTICLE DENGAN MNYAMBUNGKAN CARDADAPTER ROUTE NYA
        if (getData != null) {

            // Menetapkan data ke view
            val category: TextView = findViewById(R.id.tvTab)
            val title: TextView = findViewById(R.id.tvJudulartikel)
            val readTime: TextView = findViewById(R.id.tv_KeteranganDibaca)
            val cover: ImageView = findViewById(R.id.ivArticle)
            val content: WebView = findViewById(R.id.tvWebView)
            val source: TextView = findViewById(R.id.tvSumberLink)

            // Menetapkan data ke view berdasarkan firstArticle
            category.text = getData.category
            title.text = getData.title
            readTime.text = getData.readTime
            Picasso.get().load(getData.coverPath).into(cover)
            getData.content?.let { content.loadData(it, "text/html", "utf-8") }
            source.text = getData.source

            // Memuat data HTML ke WebView
            content.settings.javaScriptEnabled = true
            content.webViewClient = WebViewClient()
            getData.content?.let {
                content.loadDataWithBaseURL(
                    null,
                    it, "text/html", "UTF-8", null
                )
            }

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
                    <p>${getData.desc ?: ""}</p>
                    ${getData.content ?: ""}
                </body>
                </html>
    """.trimIndent()

            tvWebView.settings.javaScriptEnabled = true
            tvWebView.webViewClient = WebViewClient()
            tvWebView.loadDataWithBaseURL(null, styledContent, "text/html", "UTF-8", null)

        }

        // UNTUK RECYCLERVIEW YANG DIBAWAH SENDIRI
        rvCardArticle = findViewById(R.id.rv_cardArticle2)
        cardAdapter = CardAdapter(cardArtikelResponse)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.adapter = cardAdapter

        // MENDAPATKAN DATA MENGGUNAKAN KATA KUNCI ARTICLELIST DARI HALAMAN ARTIKEL ACTIVITY
        val articleList = intent.getParcelableArrayListExtra<CardArtikelResponse>("articleList")

        // PENERAPAN UNTUK MENGAMBIL DATANYA
        if (articleList != null && articleList.size >= 2) {
            val randomIndices = List(2) { Random.nextInt(articleList.size) }
            val randomArticles = randomIndices.map { articleList[it] }

            cardAdapter.updateData(randomArticles)

            cardAdapter.onItemClick = { selectedArticle ->
                val intent = Intent(this, DetailArticleActivity::class.java)
                intent.putExtra("articles", selectedArticle)
                intent.putParcelableArrayListExtra("articleList", ArrayList(articleList))
                startActivity(intent)
            }
        }

    }
}
