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
import com.example.finalproject_chilicare.adapter.TabAdapter
import com.example.finalproject_chilicare.data.response.CardResponse
import com.example.finalproject_chilicare.data.response.TabResponse


class DetailArticleActivity : AppCompatActivity() {

    private lateinit var rvCardArticle: RecyclerView
    private lateinit var cardAdapter: CardAdapter

    private lateinit var cardResponses: ArrayList<CardResponse>

    lateinit var cardTitleList: Array<String>
    lateinit var cardSubtitleList: Array<String>
    lateinit var cardDescriptionList: Array<String>
    lateinit var cardDurasibacaList: Array<String>
    lateinit var cardImageList: Array<Int>

    lateinit var dataImageDetails: Array<Int>
    lateinit var dataTanggalDetails: Array<String>
    lateinit var dataWaktuDetails: Array<String>
    lateinit var dataTitleDetails: Array<String>
    lateinit var dataSubtitleDetails: Array<String>
    lateinit var dataWebViewDetails: Array<String>

    private lateinit var tvWebView: WebView
    private lateinit var ivKembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)


        ivKembali = findViewById(R.id.ivKembali)
        ivKembali.setOnClickListener {
            Intent(this, ArticleActivity::class.java).also {
                startActivity(it)
            }
        }

        val getData = intent.getParcelableExtra<CardResponse>("android")
        if (getData != null) {
            val dataImageDetails: ImageView = findViewById(R.id.ivArticle)
            val dataTanggalDetails: TextView = findViewById(R.id.tvTanggal)
            val dataWaktuDetails: TextView = findViewById(R.id.tvWaktu)
            val dataTitleDetails: TextView = findViewById(R.id.tvJudulartikel)
            val dataSubtitleDetails: TextView = findViewById(R.id.tvTab)
            val dataWebViewDetails: WebView = findViewById(R.id.tvWebView)
            dataImageDetails.setImageResource(getData.dataImageDetails)
            dataTanggalDetails.text = getData.dataTanggalDetails
            dataWaktuDetails.text = getData.dataWaktuDetails
            dataTitleDetails.text = getData.dataTitleDetails
            dataSubtitleDetails.text = getData.dataSubtitleDetails
            dataWebViewDetails.loadData(getData.dataWebViewDetails, "text/html", "utf-8")
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


        cardTitleList = arrayOf(
            "Menanam",
            "Bibit",
            "Hama",
            "Pestisida",
            "Menanam",
            "Menanam",
            "Petisida",
            "Petisida",
            "Petisida",
            "Petisida"
        )
        cardSubtitleList = arrayOf(
            "Mudah Menanam Cabe Agar Cepat Berbuah",
            "Tips Memilih Bibit Cabai Yang Berkualitas",
            "Cegah Hama Pada Tanaman Cabai Anda!",
            "Membuat Pestisida Alami Tanaman Cabai",
            "Cara Mudah Menanam Cabai di Pot",
            "Teknik Budidaya Cabai Rawit",
            "Teknik Budidaya Cabai Rawit",
            "Membuat pestisida alami tanaman cabai",
            "Membuat pestisida alami tanaman cabai",
            "Membuat pestisida alami tanaman cabai"
        )
        cardDescriptionList = arrayOf(
            "Semakin banyak kegiatan seru di tengah kesibukan, salah satunya bertani",
            "Bibit yang berkualitas memiliki beberapa ciri-ciri yang bisa chili-cares amati",
            "Hama dapat menyebabkan gagal panen pada tanaman cabai Anda.",
            "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
            "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
            "Cabai rawit atau cabai kecil (Capsicum frutescens) termasuk dalamfamili Solanaceae dan merupakan tanaman berumur panjang (menahun)",
            "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
            "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
            "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi",
            "Meningkatnya kesadaran masyarakat akan efek samping menggunakan pestisida kimiawi"
        )
        cardDurasibacaList =
            arrayOf(
                "3 menit baca",
                "5 menit baca",
                "4 menit baca",
                "5 menit baca",
                "5 menit baca",
                "10 menit dibaca",
                "10 menit dibaca",
                "10 menit dibaca",
                "10 menit dibaca",
                "10 menit dibaca"
            )
        cardImageList = arrayOf(
            R.drawable.gambar_1,
            R.drawable.gambar_2,
            R.drawable.gambar_3,
            R.drawable.gambar_4,
            R.drawable.gambar_5,
            R.drawable.gambar_6,
            R.drawable.gambar_7,
            R.drawable.gambar_8,
            R.drawable.gambar_9,
            R.drawable.gambar_10
        )
        dataImageDetails = arrayOf(
            R.drawable.gambar_1,
            R.drawable.gambar_2,
            R.drawable.gambar_3,
            R.drawable.gambar_4,
            R.drawable.gambar_5,
            R.drawable.gambar_6,
            R.drawable.gambar_7,
            R.drawable.gambar_8,
            R.drawable.gambar_9,
            R.drawable.gambar_10
        )
        dataTanggalDetails = arrayOf(
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021",
            "19 Januari 2021"
        )
        dataWaktuDetails = arrayOf(
            "19:00 WIB",
            "19:00 WIB",
            "19:00 WIB",
            "19:00 WIB",
            "19:00 WIB",
            "19:00 WIB",
            "19:00 WIB",
            "19:00 WIB"
        )
        dataTitleDetails = arrayOf(
            "Mudah Menanam Cabe Agar Cepat Berbuah",
            "Tips Memilih Bibit Cabai Yang Berkualitas",
            "Cegah Hama Pada Tanaman Cabai",
            "Membuat Pestisida Alami Tanaman Cabai",
            "Cara Mudah Menanam Cabai di Pot",
            "Teknik Budidaya Cabai Rawit",
            "Teknik Budidaya Cabai Rawit",
            "Membuat pestisida alami tanaman cabai"
        )
        dataSubtitleDetails = arrayOf(
            "Menanam",
            "Bibit",
            "Hama",
            "Pestisida",
            "Menanam",
            "Menanam",
            "Petisida",
            "Petisida",
            "Petisida",
            "Petisida"
        )
        dataWebViewDetails = Array(cardImageList.size) { "" }

        rvCardArticle = findViewById(R.id.rv_cardArticle2)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.setHasFixedSize(true)

        cardResponses = arrayListOf<CardResponse>()
        getDataCard()

        cardAdapter = CardAdapter(cardResponses)
        rvCardArticle.adapter = cardAdapter
        cardAdapter.onItemClick = {
            Log.d("ArticleActivity", "Clicked item: $it")
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }
    }

    private fun getDataCard() {
        cardResponses.clear()
        for (i in cardTitleList.indices) {
            val cardResponse = CardResponse(
                cardTitleList[i],
                cardSubtitleList[i],
                cardDescriptionList[i],
                cardDurasibacaList[i],
                cardImageList[i],
                dataImageDetails[i],
                dataTanggalDetails[i],
                dataWaktuDetails[i],
                dataTitleDetails[i],
                dataSubtitleDetails[i],
                dataWebViewDetails[i]
            )
            cardResponses.add(cardResponse)
        }
    }
}
