package com.example.finalproject_chilicare.ui.lms

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardAllModulResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import com.example.finalproject_chilicare.databinding.ActivityMateriLmsactivityBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class MateriLMSActivity : AppCompatActivity(), CardLmsMateriAdapter.ItemClickListener {
    lateinit var bindingMateri: ActivityMateriLmsactivityBinding
    lateinit var prefHelper: SharedPreferences

    lateinit var ivBack: ImageView
    lateinit var ivMore: ImageView
    private val listModul = ArrayList<ListMateriLMS>()
    lateinit var cdmateriadapter: CardLmsMateriAdapter
    lateinit var rvMateriLms: RecyclerView
    private var materilistlms = mutableListOf<ListMateriLMS>()
    lateinit var titlemateri: TextView
    lateinit var descmateri: TextView
    lateinit var totalmateri: TextView
    lateinit var learningtime: TextView
    lateinit var datemateri: TextView
    lateinit var covermateri: ImageView
    private var judulMateri = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi_lmsactivity)

        bindingMateri = DataBindingUtil.setContentView(this, R.layout.activity_materi_lmsactivity)
        prefHelper = PreferencesHelper.customMateriLms(this@MateriLMSActivity)

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
        Log.d("LMS", "menampilkan recyclerview ${rvMateriLms} ")
        cdmateriadapter = CardLmsMateriAdapter(materilistlms, this)
        rvMateriLms.adapter = cdmateriadapter
        rvMateriLms.setHasFixedSize(true)


        //get API Lifecycle scope
        lifecycleScope.launch {
            val id = intent.getStringExtra("id").toString()
            Log.d("lms_id", "onCreate: $id ")
            Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getMateribyId(id).enqueue(object :
                    retrofit2.Callback<CardAllModulResponse> {
                    override fun onResponse(
                        call: Call<CardAllModulResponse>,
                        response: Response<CardAllModulResponse>
                    ) {
                        Log.d(TAG, "onResponse: respose body ${response}")
                        if (response.isSuccessful) {
                            Log.d(TAG, "onResponse: materilist body ${response.body()}")
                            val responses = response.body()

                            titlemateri.text = responses!!.data[0].judul.toString()
                            descmateri.text = responses.data[0].desc.toString()
                            learningtime.text = responses.data[0].learningTime.toString()
                            totalmateri.text = responses.data[0].totalMateri.toString()+" Materi"
                            datemateri.text = responses.data[0].tanggal
                            val path = buildIcobpath(responses.data[0].covers)
                            Picasso.get().load(path).into(covermateri, object : Callback {
                                override fun onSuccess() {
                                    Log.d("LmsMateri", "image success load")
                                }

                                override fun onError(e: Exception?) {
                                    Log.e("LmsMateri", "error load image ${e?.message}")
                                }
                            })

                            //set recyclerView
                            val rvMateri = bindingMateri.rvCardLmsmateri
                            rvMateri.setHasFixedSize(true)
                            rvMateri.layoutManager = LinearLayoutManager(this@MateriLMSActivity)

//                            Log.d(TAG, "setMateri: data  ${body.data}")
                            val adapterMateri = responses.data[0].listMateri?.let {
                                CardLmsMateriAdapter(
                                    it, this@MateriLMSActivity
                                )
                            }
                            rvMateri.adapter = adapterMateri


                        } else {
                            Log.d(TAG, "onResponse: failed to get data ")
                        }

                    }

                    override fun onFailure(call: Call<CardAllModulResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }


                })


            //Update recyclerview
            cdmateriadapter.notifyDataSetChanged()

        }


        // Klik item menuju Detail Lms activity
//        cdmateriadapter.cardClick =
//            {
//                Log.d("LMS", "klik materi Lms: ${it}")
//                val intent = Intent(this, DetailLMSActivity::class.java)
//                intent.putExtra("materiLms", it)
//                intent.putParcelableArrayListExtra("DetailMateri", ArrayList(materilistlms))
//                startActivity(intent)
//            }

    }
    override fun onItemClick(position: Int) {
        // Tangani klik di sini
        val id = intent.getStringExtra("id").toString()
        val intent = Intent(this, DetailLMSActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    private fun buildIcobpath(icon: String?): String {
        return icon ?: ""
    }


}