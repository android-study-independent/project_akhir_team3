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
import com.example.finalproject_chilicare.data.response.CardResponse
import com.example.finalproject_chilicare.utils.DummyDataArtikel

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var rvCardArticle: RecyclerView
    private lateinit var cardAdapter: CardAdapter

    private lateinit var cardResponses: ArrayList<CardResponse>

    private lateinit var tvWebView: WebView
    private lateinit var ivKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)

        // Menginisialisasi view
        ivKembali = findViewById(R.id.ivKembali)
        tvWebView = findViewById(R.id.tvWebView)
        rvCardArticle = findViewById(R.id.rv_cardArticle2)

        // Set listener untuk tombol kembali
        ivKembali.setOnClickListener {
            Intent(this, ArticleActivity::class.java).also {
                startActivity(it)
            }
        }

        // Mendapatkan data dari intent
        val getData = intent.getParcelableExtra<CardResponse>("android")
        if (getData != null) {
            // Menetapkan data ke view
            val dataImageDetails: ImageView = findViewById(R.id.ivArticle)
            val dataTanggalDetails: TextView = findViewById(R.id.tvTanggal)
            val dataWaktuDetails: TextView = findViewById(R.id.tvWaktu)
            val dataTitleDetails: TextView = findViewById(R.id.tvJudulartikel)
            val dataSubtitleDetails: TextView = findViewById(R.id.tvTab)
            val dataWebViewDetails: WebView = findViewById(R.id.tvWebView)

            // Menetapkan data ke view
            dataImageDetails.setImageResource(getData.dataImageDetails)
            dataTanggalDetails.text = getData.dataTanggalDetails
            dataWaktuDetails.text = getData.dataWaktuDetails
            dataTitleDetails.text = getData.dataTitleDetails
            dataSubtitleDetails.text = getData.dataSubtitleDetails
            dataWebViewDetails.loadData(getData.dataWebViewDetails, "text/html", "utf-8")

            // Memuat data HTML ke WebView
            tvWebView.settings.javaScriptEnabled = true
            tvWebView.webViewClient = WebViewClient()
            tvWebView.loadDataWithBaseURL(null, getData.dataWebViewDetails, "text/html", "UTF-8", null)
        }

        tvWebView = findViewById(R.id.tvWebView)
        val htmlText = """
            <html>
            <body>
                <p style="text-align: justify; font-family: @font/plusjakartasans_reguler_400; font-size: 14px; color: @color/SecondaryText;">
                    Dengan banyaknya nutrisi yang terkandung dalam cabai didukung dengan maraknya makanan pedas sebagai bisnis di bidang makanan dan jajanan pedas yang berdampak kepada para petani cabai. Ingin tanaman cabai Anda cepat berbuah? Simak beberapa cara berikut ini.
                </p>
                <ol style="font-family: @font/plusjakartasans_reguler_400; font-size: 14px; color: @color/SecondaryText;">
                    <li>Pilih benih cabai berkualitas</li>
                    <li>Keluarkan biji cabai dari nuahnya</li>
                    <li>Jemur biji cabai</li>
                    <li>Seleksi biji cabai</li>
                    <li>Mulai penyemaian</li>
                    <li>Pindahkan ke media tanam</li>
                    <li>Mulai perawatan tanaman cabai</li>
                </ol>
                <p style="text-align: justify; font-family: @font/plusjakartasans_reguler_400; font-size: 14px; color: @color/SecondaryText;">
                    Itulah informasi mengenai mudah menanam cabai agar cepat berbuah.
                    <br/><br/>
                    Sumber: <a href="https://ketahananpangan.semarangkota.go.id/v3/portal/page/artikel/7-Cara-Mudah-Menanam-Cabe-Agar-Cepat-Berbuah">https://ketahananpangan.semarangkota.go.id/v3/portal/page/artikel/7-Cara-Mudah-Menanam-Cabe-Agar-Cepat-Berbuah</a>
                </p>
            </body>
            </html>
        """.trimIndent()

        tvWebView.settings.javaScriptEnabled = true
        tvWebView.webViewClient = WebViewClient()
        tvWebView.loadDataWithBaseURL(null, htmlText, "text/html", "UTF-8", null)



        val cardResponses: MutableList<CardResponse> = DummyDataArtikel.getDummyCardResponses().toMutableList()

        val filteredCardResponses = cardResponses.take(2).toMutableList()


        // Inisialisasi dan atur adapter CardAdapter untuk RecyclerView card
        cardAdapter = CardAdapter(filteredCardResponses)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.setHasFixedSize(true)
        rvCardArticle.adapter = cardAdapter

        // Menangani klik item di RecyclerView card
        cardAdapter.onItemClick = {
            Log.d("ArticleActivity", "Clicked item: $it")
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }
}
