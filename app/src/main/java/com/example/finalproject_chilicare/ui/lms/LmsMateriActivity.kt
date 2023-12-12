package com.example.finalproject_chilicare.ui.lms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.lang.Exception

class LmsMateriActivity : AppCompatActivity() {
    private var listMateri = ArrayList<ListMateriLMS>()

    private lateinit var rvMateri : RecyclerView


    lateinit var btnback : ImageView
    lateinit var cardAdapaterList : CardLmsMateriAdapter
    lateinit var judul : TextView
    lateinit var descript : TextView
    lateinit var totalmateri : TextView
    lateinit var timelearning : TextView
    lateinit var tanggal : TextView
    lateinit var imagetv : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lms_materi)

        //inisiasi XML layoutnya
        btnback = findViewById(R.id.btnBackLmslist)
        judul = findViewById(R.id.labelJuduMateri)
        descript = findViewById(R.id.labelshortdesc)
        totalmateri = findViewById(R.id.tvtotalmaterilms)
        timelearning = findViewById(R.id.tvtimelms)
        tanggal = findViewById(R.id.tvdateMateriLms)
        imagetv = findViewById(R.id.imgLmsMateri)

        //Inisiasi ketika back dipencet kembali ke halaman beranda LmsActivity
        btnback.setOnClickListener {
//            Intent(this, HomeActivity::class.java).also {
//                startActivity(it)
//            }
            onBackPressed()
        }

        //card lms list materi
        rvMateri = findViewById(R.id.rv_ListMateriLms)
        rvMateri.setHasFixedSize(true)
        val materiLmslistAdapter = CardLmsMateriAdapter(listMateri)
        rvMateri.adapter = materiLmslistAdapter
        rvMateri.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getMateriLms()
            result.listMateri?.let { materiList ->
                listMateri.addAll(materiList.reversed())
                materiLmslistAdapter.notifyDataSetChanged()
            }

        }
    }


    fun setDataModulist(body: CardLmsResponse?) {
        body?.let {
            judul.text = it.judul
            descript.text = it.desc
            timelearning.text = it.learningTime
            totalmateri.text = it.totalMateri.toString()
            tanggal.text = it.tanggal

            val path = buildIcobpath(it.covers)
            Picasso.get().load(path).into(imagetv, object : Callback {
                override fun onSuccess() {
                    Log.d("LmsMateri", "image success load")
                }

                override fun onError(e: Exception?) {
                    Log.e("LmsMateri", "error load image ${e?.message}")
                }
            })
        } ?: run {
            Log.e("LmsMateri", "setDataModul called with null body")
        }

    }

    private fun buildIcobpath(icon: String?): String {
        return icon ?: ""
    }
}