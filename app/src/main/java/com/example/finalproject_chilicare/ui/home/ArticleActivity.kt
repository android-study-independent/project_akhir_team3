package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.CardAdapter
import com.example.finalproject_chilicare.data.response.CardResponse
import com.example.finalproject_chilicare.adapter.TabAdapter
import com.example.finalproject_chilicare.data.response.TabResponse

class ArticleActivity : AppCompatActivity() {

    private lateinit var rvTabArticle: RecyclerView
    private lateinit var rvCardArticle: RecyclerView

    private lateinit var tabResponses: ArrayList<TabResponse>
    private lateinit var cardResponses: ArrayList<CardResponse>

    lateinit var tabTitleList: Array<String>

    lateinit var cardTitleList: Array<String>
    lateinit var cardSubtitleList: Array<String>
    lateinit var cardDescriptionList: Array<String>
    lateinit var cardDurasibacaList: Array<String>
    lateinit var cardImageList: Array<Int>

    // 1 keperluan detail artikel
    lateinit var dataImageDetails: Array<Int>
    lateinit var dataTanggalDetails: Array<String>
    lateinit var dataWaktuDetails: Array<String>
    lateinit var dataTitleDetails: Array<String>
    lateinit var dataSubtitleDetails: Array<String>
    lateinit var dataWebViewDetails: Array<String>

    lateinit var etCariArtikel: EditText
    lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        etCariArtikel = findViewById(R.id.etCariArtikel)


        tabTitleList = arrayOf("Menanam", "Bibit", "Hama", "Tanah", "Pestisida")

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
            "Membuat pestisida alami tanaman cabai",
            "Membuat pestisida alami tanaman cabai",
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



        // RecyclerView tab
        rvTabArticle = findViewById(R.id.rv_tabArticle)
        rvTabArticle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTabArticle.setHasFixedSize(true)

        // RecyclerView card
        rvCardArticle = findViewById(R.id.rv_cardArticle)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.setHasFixedSize(true)

        tabResponses = ArrayList()
        cardResponses = arrayListOf<CardResponse>()
        getDataTab()
        getDataCard()

        // Inisialisasi dan atur adapter CardAdapter
        cardAdapter = CardAdapter(cardResponses)
        rvCardArticle.adapter = cardAdapter
        cardAdapter.onItemClick = {
            Log.d("ArticleActivity", "Clicked item: $it")
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("android", it)
            startActivity(intent)
        }

        etCariArtikel.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterList(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed, but must be implemented
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed, but must be implemented
            }
        })
    }

    private fun filterList(query: String?) {
        cardAdapter.filter.filter(query)
    }

    private fun getDataTab() {
        for (i in tabTitleList.indices) {
            val dataTabResponse = TabResponse( tabTitleList[i])
            tabResponses.add(dataTabResponse)
        }

        rvTabArticle.adapter = TabAdapter(tabResponses)
    }

    private fun getDataCard() {
        for (i in cardImageList.indices) {
            if (i < cardTitleList.size &&
                i < cardSubtitleList.size &&
                i < cardDescriptionList.size &&
                i < cardDurasibacaList.size &&
                i < dataImageDetails.size &&
                i < dataTanggalDetails.size &&
                i < dataWaktuDetails.size &&
                i < dataTitleDetails.size &&
                i < dataSubtitleDetails.size &&
                i < dataWebViewDetails.size
            ) {
                val card = CardResponse(
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
                cardResponses.add(card)
            } else {
                Log.e(
                    "ArticleActivity",
                    "Index out of bounds: i=$i, " +
                            "cardTitleList.size=${cardTitleList.size}, " +
                            "cardSubtitleList.size=${cardSubtitleList.size}, " +
                            "cardDescriptionList.size=${cardDescriptionList.size}, " +
                            "cardDurasibacaList.size=${cardDurasibacaList.size}, " +
                            "dataImageDetails.size=${dataImageDetails.size}, " +
                            "dataTanggalDetails.size=${dataTanggalDetails.size}, " +
                            "dataWaktuDetails.size=${dataWaktuDetails.size}, " +
                            "dataTitleDetails.size=${dataTitleDetails.size}, " +
                            "dataSubtitleDetails.size=${dataSubtitleDetails.size}, " +
                            "dataWebViewDetails.size=${dataWebViewDetails.size}"
                )
            }
        }
    }

}