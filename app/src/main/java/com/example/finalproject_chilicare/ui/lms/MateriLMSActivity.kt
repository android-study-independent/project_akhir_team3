package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardAllModulResponse
import com.example.finalproject_chilicare.data.response.lms.CardLmsResponse
import com.example.finalproject_chilicare.data.response.lms.DataModulResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import com.example.finalproject_chilicare.databinding.ActivityMateriLmsactivityBinding
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.sign
import kotlin.random.Random

class MateriLMSActivity : AppCompatActivity() {
    lateinit var binding : ActivityMateriLmsactivityBinding
    lateinit var ivBack: ImageView
    lateinit var ivMore: ImageView
    private val listModul = ArrayList<CardLmsResponse>()
    lateinit var cdmateriadapter: CardLmsMateriAdapter
    lateinit var rvMateriLms: RecyclerView
    private var materilistlms = mutableListOf<ListMateriLMS>()
    lateinit var titlemateri: TextView
    lateinit var descmateri: TextView
    lateinit var totalmateri: TextView
    lateinit var learningtime: TextView
    lateinit var datemateri: TextView
    lateinit var covermateri: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi_lmsactivity)
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_materi_lmsactivity)

        //inisasi layout xml
        ivBack = findViewById(R.id.btnBackMateriLms)

        //Inisiasi ketika back dipencet kembali ke halaman beranda LmsActivity
        ivBack.setOnClickListener {
//            Intent(this, HomeActivity::class.java).also {
//                startActivity(it)
//            }
            onBackPressed()
        }

        //Inisasi XML MODULnya
        titlemateri = findViewById(R.id.tvJudulMateri)
        descmateri = findViewById(R.id.tvmateriLms)
        covermateri = findViewById(R.id.ivImgmainlms)
        learningtime = findViewById(R.id.tvWatchTime)
        totalmateri = findViewById(R.id.tvtotalmateri)
        datemateri = findViewById(R.id.tv_datematerilms)


        // card materi lms
        rvMateriLms = findViewById(R.id.rv_cardLmsmateri)
        Log.d("LMS","menampilkan recyclerview ${rvMateriLms} ")
        cdmateriadapter = CardLmsMateriAdapter(materilistlms)
        rvMateriLms.adapter = cdmateriadapter
        rvMateriLms.setHasFixedSize(true)

        //get API Lifecycle scope
        lifecycleScope.launch {
            val result = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getMateriLms()
            result.listMateri?.let { materiList ->
                materilistlms.addAll(materiList.reversed())
                cdmateriadapter.notifyDataSetChanged()
                rvMateriLms.adapter = cdmateriadapter
                rvMateriLms.layoutManager = LinearLayoutManager(this@MateriLMSActivity,RecyclerView.VERTICAL,false)
                Log.d("LMS","hasil get API materi ${result}")

            }
            val hasil = Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getAllLms()
            hasil.data.map {
                Log.d("LMS", "hasil get API modul : ${it}")
                setDataModul(it)

            }

            //Update recyclerview
            cdmateriadapter.notifyDataSetChanged()

        }

        // mendapatkan data dengan key dari LMS
        val modulList = intent.getParcelableArrayListExtra<ListMateriLMS>("modulLms")

        //penereapan ambil data
        if(modulList != null && modulList.size >=1) {
            val randomIndicies = List(1) {Random.nextInt(modulList.size)}
//            val randomModulLms = randomIndicies.map { modulList[it] }
//
//            cdmateriadapter.updateData(randomModulLms)

            cdmateriadapter.cardClick= { selectedModul->
                Log.d("LMS","click item ${cdmateriadapter}")
                val intent = Intent(this,DetailLMSActivity::class.java)
                intent.putParcelableArrayListExtra("modulLms",ArrayList(modulList))
                startActivity(intent)
            }
        }

        // Klik item menuju Detail Lms activity
        cdmateriadapter.cardClick ={
            Log.d("LMS","klik materi Lms: ${it}")
            val intent = Intent(this, DetailLMSActivity::class.java)
            intent.putExtra("materiLms",it)
            intent.putParcelableArrayListExtra("DetailMateri", ArrayList(materilistlms))
            startActivity(intent)
        }

    }

//    fun setDataMateriItem(body : CardAllModulResponse) {
//        binding.apply {
//            cdmateriadapter = CardLmsMateriAdapter(body.data)
//            val rvMateriItem = binding.rvCardLmsmateri
//            rvMateriItem.layoutManager = LinearLayoutManager(this@MateriLMSActivity,RecyclerView.VERTICAL,false)
//            rvMateriItem.setHasFixedSize(true)
//            rvMateriItem.adapter = cdmateriadapter
//            cdmateriadapter.notifyDataSetChanged()
//        }
//    }

    fun setDataModul(body: CardLmsResponse?) {
        body?.let {
            titlemateri.text = it.judul
            descmateri.text = it.desc
            learningtime.text = it.learningTime
            totalmateri.text = it.totalMateri.toString()
            datemateri.text = it.tanggal

            val path = buildIcobpath(it.covers)
            Picasso.get().load(path).into(covermateri, object : Callback {
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