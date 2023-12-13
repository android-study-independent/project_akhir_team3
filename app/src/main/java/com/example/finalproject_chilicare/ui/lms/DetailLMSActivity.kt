package com.example.finalproject_chilicare.ui.lms

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.lms.CardLmsMateriAdapter
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.lms.ListMateriLMS
import kotlinx.coroutines.launch

class DetailLMSActivity : AppCompatActivity() {

    private lateinit var cardAdapter: CardLmsMateriAdapter
    private lateinit var ivKembali: ImageView
    private lateinit var rvCardLMS: RecyclerView
    private lateinit var videoView: VideoView
    private var cardListMateriLMS = mutableListOf<ListMateriLMS>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lmsactivity)

        ivKembali = findViewById(R.id.iv_Kembali)
        videoView = findViewById(R.id.VideoView)
        rvCardLMS = findViewById(R.id.rv_cardLms)

        // Tombol Kembali
        ivKembali.setOnClickListener {
            finish()
        }

        // Initialize RecyclerView for CardLMS
        rvCardLMS.layoutManager = LinearLayoutManager(this)
        cardAdapter = CardLmsMateriAdapter(cardListMateriLMS)
        rvCardLMS.adapter = cardAdapter

        // Get data API using lifecycleScope
        lifecycleScope.launch {
            try {
                val apiService = Network().getRetroClientInstance().create(ApiInterface::class.java)
                val result = apiService.getAllLms()

                result.data?.let { cardLmsResponse ->
                    cardListMateriLMS.clear()
                    cardListMateriLMS.addAll(cardLmsResponse.flatMap { it.listMateri.map { listMateri ->
                        ListMateriLMS(
                            youtube = listMateri.youtube,
                            longDesc = listMateri.longDesc,
                            shortDesc = listMateri.shortDesc,
                            judulMateri = listMateri.judulMateri,
                            image = listMateri.image
                        )
                    } })

                    // Update RecyclerView
                    cardAdapter.updateData(cardListMateriLMS)

                    // Klik item menuju Detail Lms activity
                    cardAdapter.cardClick = { selectedMateri ->
                        val intent = Intent(this@DetailLMSActivity, DetailLMSActivity::class.java)
                        intent.putExtra("youtube", selectedMateri.youtube)
                        intent.putExtra("longDesc", selectedMateri.longDesc)
                        intent.putExtra("shortDesc", selectedMateri.shortDesc)
                        intent.putExtra("judulMateri", selectedMateri.judulMateri)
                        intent.putExtra("image", selectedMateri.image)
                        startActivity(intent)
                    }
                } ?: showPlaceholderImage()
            } catch (e: Exception) {
                e.printStackTrace()
                showPlaceholderImage()
            }
        }
    }

    // Untuk memanggil video
    private fun playVideo(videoUrl: String) {
        try {
            val uri = Uri.parse(videoUrl)
            videoView.setVideoURI(uri)
            videoView.start()
        } catch (e: Exception) {
            e.printStackTrace()
            showPlaceholderImage()
        }
    }

    // Kalau video gak muncul tampilkan gambar
    private fun showPlaceholderImage() {
        val placeholderImageView: ImageView = findViewById(R.id.placeholderImageView)
        placeholderImageView.setImageResource(R.drawable.bg_article)
    }
}
