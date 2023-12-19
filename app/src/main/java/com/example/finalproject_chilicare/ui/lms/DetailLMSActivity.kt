package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.CardAllModulResponse
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import com.example.finalproject_chilicare.databinding.ActivityDetailLmsactivityBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Pattern
import retrofit2.Call
import retrofit2.Response


class DetailLMSActivity : AppCompatActivity(), CardLmsMateriAdapter.ItemClickListener {
    lateinit var bindingDetailLms: ActivityDetailLmsactivityBinding
    lateinit var prefHelper: SharedPreferences

    lateinit var cardMateriAdapter: CardLmsMateriAdapter
    private lateinit var rvCardMateri: RecyclerView
    private var cardMateriResponse = mutableListOf<ListMateriLMS>()
    private lateinit var tvVideo: YouTubePlayerView
    private lateinit var tvLongDesc: TextView
    private lateinit var ytView: TextView


    lateinit var iv_Kembali: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_detail_lmsactivity)

        bindingDetailLms =
            DataBindingUtil.setContentView(this, R.layout.activity_detail_lmsactivity)
        prefHelper = PreferencesHelper.customDetailMateriLms(this@DetailLMSActivity)


        //INISIASI LAYOUT XML
        iv_Kembali = findViewById(R.id.iv_KembaliDtlLms)
        tvVideo = findViewById(R.id.youtube_player_view)

        //Tombol untuk kembali
        iv_Kembali.setOnClickListener {
            Intent(this, MateriLMSActivity::class.java).also {
                onBackPressed()
            }
        }

        //get API Lifecycle scope
        lifecycleScope.launch {
            val id = intent.getStringExtra("id").toString()
            val position = intent.getIntExtra("position", 0).toString().toInt()
            Log.d("detail_lms id", "onCreate: $id")
            Network().getRetroClientInstance()
                .create(ApiInterface::class.java).getMateribyId(id)
                .enqueue(object : retrofit2.Callback<CardAllModulResponse> {
                    override fun onResponse(
                        call: Call<CardAllModulResponse>,
                        response: Response<CardAllModulResponse>
                    ) {
                        Log.d("detail_lms_id", "onResponse: response body ${response}")
                        if (response.isSuccessful) {
                            Log.d("detail_lms_id", "onResponse: materidetail body ${response} ")
                            val responses = response.body()
//                            if (responses != null && !responses.data.isNullOrEmpty()) {
//                                val listMateriitem = responses.data[0].listMateri
//                                if (listMateriitem !=null && listMateriitem.isNotEmpty() && position <listMateriitem.size) {
//                                    bindingDetailLms.tvJudulMateri.text = listMateriitem[position].judulMateri
//                                    bindingDetailLms.tvDescLms.text = listMateriitem[position].longDesc
//                                } else{
//                                    Log.e("detail_lms", "eror load data ", )
//                                }
//                            } else {
//                                Log.e("detail_lms", "onResponse: ", )
//                            }


                            //SETTNG link youtube
                            val youtubeUrl =  responses!!.data[0].listMateri!![position].youtube
                            val youTubePlayerView = bindingDetailLms.youtubePlayerView
                            val videoId = youtubeUrl?.let { extractVideoId(it) }
                            youTubePlayerView.addYouTubePlayerListener(object :
                                AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    // Memuat video dengan video ID yang diambil dari response API
                                    youTubePlayer.loadVideo(videoId!!.toString(), 0F)
                                }

                                override fun onError(
                                    youTubePlayer: YouTubePlayer,
                                    error: PlayerConstants.PlayerError
                                ) {
                                    // Handle error jika terjadi
                                    Toast.makeText(
                                        this@DetailLMSActivity,
                                        "Terjadi kesalahan: ${error.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                            bindingDetailLms.tvJudulMateri.text =
                                responses!!.data[0].listMateri!![position].judulMateri
                            bindingDetailLms.tvDescLms.text =
                                responses!!.data[0].listMateri!![position].longDesc

                            //set recylerview
                            val rvMateri = bindingDetailLms.rvMateriDetailLms
                            rvMateri.setHasFixedSize(true)
                            rvMateri.layoutManager = LinearLayoutManager(this@DetailLMSActivity)

                            val adapterDetail = responses.data[0].listMateri?.let {
                                CardLmsMateriAdapter(it, this@DetailLMSActivity)
                            }
                            rvMateri.adapter = adapterDetail


                        } else {
                            Log.d("detail_lms_id", "onResponse: failed to get data")
                        }

                    }

                    override fun onFailure(call: Call<CardAllModulResponse>, t: Throwable) {
                        Log.e("detailLms", "onFailure: ${t?.message}")
                    }
                })


        }

//        // Mendapatkan Data dari Lms activity
//        val getDataLms = intent.getParcelableExtra<ListMateriLMS>("materiLms")
//
//        //Penerapan ambil data materi modul dari activity modul Lms
//        if (getDataLms != null) {
//            //deklarasi data ke view
//            tvLongDesc = findViewById(R.id.tv_DescLms)
//            ytView = findViewById(R.id.youtube_player_view)
//
//            //mengirim data ke view
//            tvLongDesc.text = getDataLms.longDesc
//            ytView.text = getDataLms.youtube
//        }

        //RecyclerView Materi Lanjutan
//        rvCardMateri = findViewById(R.id.rv_MateriDetailLms)
//        cardMateriAdapter = CardLmsMateriAdapter((cardMateriResponse),this)
//        rvCardMateri.layoutManager = LinearLayoutManager(this)
//        rvCardMateri.adapter = cardMateriAdapter
//
//        //mendapatkan data dari kata kunci
//        val DetailMateriLms = intent.getParcelableArrayListExtra<ListMateriLMS>("DetailMateri")
//
//        //Penerapan Ambil data
//        if (DetailMateriLms != null && DetailMateriLms.size > 1) {
//            val randomIndicies = List(1) { Random.nextInt(DetailMateriLms.size)}
//            val randomMateriLms = randomIndicies.map { DetailMateriLms[it] }
//
//            cardMateriAdapter.updateData(randomMateriLms)
//
//            cardMateriAdapter.cardClick = { selectedMateri ->
//                val intent = Intent(this,DetailLMSActivity::class.java)
//                intent.putExtra("materiLms", selectedMateri)
//                intent.putParcelableArrayListExtra("DetailMateri", ArrayList(DetailMateriLms))
//                startActivity(intent)
//            }
//
//        }
    }


    fun extractVideoId(youtubeUrl: String): String {
        val pattern =
            "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed" +
                    "%2Fvideos%2F|youtu.be%2F|v=|e\\/|u\\/\\w\\/|v5\\/|vi\\/|vjo\\/|vli\\/|vl\\/|vg\\/|vi%2F|vi%2F)([^\\" +
                    "?\\s]*)(?=[^\\w-?]*)(?:[\\w-]*[^\\w-?]+)?"
        val compiledPattern = java.util.regex.Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(youtubeUrl)

        return if (matcher.find()) {
            matcher.group()
        } else {
            // Jika tidak dapat menemukan video ID, kembalikan nilai default atau tampilkan pesan kesalahan
            "eror laod link"
        }
    }

    override fun onItemClick(position: Int) {
        // Tangani klik di sini
        val DetailMateriLms = intent.getParcelableArrayListExtra<ListMateriLMS>("DetailMateri")
        val intent = Intent(this, DetailLMSActivity::class.java)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}