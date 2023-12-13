package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import kotlin.random.Random

class DetailLMSActivity : AppCompatActivity() {
    lateinit var cardMateriAdapter : CardLmsMateriAdapter
    private lateinit var rvCardMateri: RecyclerView
    private var cardMateriResponse = mutableListOf<ListMateriLMS>()
    private lateinit var tvVideo : VideoView
    private lateinit var tvLongDesc : TextView
    private    lateinit var ytView : TextView


    lateinit var iv_Kembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lmsactivity)

        //INISIASI LAYOUT XML
        iv_Kembali = findViewById(R.id.iv_KembaliDtlLms)

        //Tombol untuk kembali
        iv_Kembali.setOnClickListener {
            Intent(this, MateriLMSActivity::class.java).also {
                startActivity(it)
            }
        }

        // Mendapatkan Data dari Lms activity
        val getDataLms = intent.getParcelableExtra<ListMateriLMS>("materiLms")

        //Penerapan ambil data materi modul dari activity modul Lms
        if (getDataLms != null) {
            //deklarasi data ke view
            tvLongDesc = findViewById(R.id.tv_DescLms)
            ytView = findViewById(R.id.youtube_player_view)

            //mengirim data ke view
            tvLongDesc.text = getDataLms.longDesc
            ytView.text = getDataLms.youtube
        }

        //RecyclerView Materi Lanjutan
        rvCardMateri = findViewById(R.id.rv_MateriLms)
        cardMateriAdapter = CardLmsMateriAdapter((cardMateriResponse))
        rvCardMateri.layoutManager = LinearLayoutManager(this)
        rvCardMateri.adapter = cardMateriAdapter

        //mendapatkan data dari kata kunci
        val DetailMateriLms = intent.getParcelableArrayListExtra<ListMateriLMS>("DetailMateri")

        //Penerapan Ambil data
        if (DetailMateriLms != null && DetailMateriLms.size > 1) {
            val randomIndicies = List(1) { Random.nextInt(DetailMateriLms.size)}
            val randomMateriLms = randomIndicies.map { DetailMateriLms[it] }

            cardMateriAdapter.updateData(randomMateriLms)

            cardMateriAdapter.cardClick = { selectedMateri ->
                val intent = Intent(this,DetailLMSActivity::class.java)
                intent.putExtra("materiLms", selectedMateri)
                intent.putParcelableArrayListExtra("DetailMateri", ArrayList(DetailMateriLms))
                startActivity(intent)
            }

            //    private fun getListModul(): ArrayList<DataModulResponse>{
//        val shortdesc = resources.getStringArray(R.array.data_shortdesc)
//        val titlemodul = resources.getStringArray(R.array.data_titlemodul)
//        val covermodul = resources.obtainTypedArray(R.array.datacovermodul)
//        val listmodul = ArrayList<DataModulResponse>()
//        for (i in titlemodul.indices) {
//            val user = DataModulResponse(
//
//                titlemodul[i],shortdesc[i],
//                covermodul.getResourceId(i,-1)
//            )
//            listmodul.add(user)
//        }
//        return  listmodul
//    }
        }
    }
}