package com.example.finalproject_chilicare.ui.home.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.ui.home.article.card.CardAdapter
import com.example.finalproject_chilicare.ui.home.article.card.CardClass
import com.example.finalproject_chilicare.ui.home.article.tab.TabAdapter
import com.example.finalproject_chilicare.ui.home.article.tab.TabClass
import java.util.Locale

class ArticleActivity : AppCompatActivity() {

    private lateinit var rvTabArticle: RecyclerView
    private lateinit var rvCardArticle: RecyclerView

    private lateinit var tabClass: ArrayList<TabClass>
    private lateinit var cardClass: ArrayList<CardClass>

    lateinit var tabImageList: Array<Int>
    lateinit var tabTitleList: Array<String>

    lateinit var cardTitleList: Array<String>
    lateinit var cardSubtitleList: Array<String>
    lateinit var cardDescriptionList: Array<String>
    lateinit var cardDurasibacaList: Array<String>
    lateinit var cardImageList: Array<Int>

    lateinit var etCariArtikel: EditText
    lateinit var cardAdapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        etCariArtikel = findViewById(R.id.etCariArtikel)

        tabImageList = arrayOf(R.drawable.ic_filter)
        tabTitleList = arrayOf("Filter", "Menanam", "Bibit", "Hama", "Tanah", "Pestisida")

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

        // RecyclerView tab
        rvTabArticle = findViewById(R.id.rv_tabArticle)
        rvTabArticle.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvTabArticle.setHasFixedSize(true)

        // RecyclerView card
        rvCardArticle = findViewById(R.id.rv_cardArticle)
        rvCardArticle.layoutManager = LinearLayoutManager(this)
        rvCardArticle.setHasFixedSize(true)

        tabClass = ArrayList()
        cardClass = ArrayList()
        getDataTab()
        getDataCard()

        // Inisialisasi dan atur adapter CardAdapter
        cardAdapter = CardAdapter(cardClass)
        rvCardArticle.adapter = cardAdapter

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
        for (i in tabImageList.indices) {
            val dataTabClass = TabClass(tabImageList[i], tabTitleList[i])
            tabClass.add(dataTabClass)
        }

        // Tambahkan item yang tidak memiliki ikon filter di sini
        tabClass.add(TabClass(0, "Tanah"))
        tabClass.add(TabClass(0, "Menanam"))
        tabClass.add(TabClass(0, "Bibit"))
        tabClass.add(TabClass(0, "Hama"))
        tabClass.add(TabClass(0, "Pestisida"))

        rvTabArticle.adapter = TabAdapter(tabClass)
    }

    private fun getDataCard() {
        for (i in cardImageList.indices) {
            val dataCardClass = CardClass(
                cardTitleList[i],
                cardSubtitleList[i],
                cardDescriptionList[i],
                cardDurasibacaList[i],
                cardImageList[i]
            )
            cardClass.add(dataCardClass)
        }
    }
}
