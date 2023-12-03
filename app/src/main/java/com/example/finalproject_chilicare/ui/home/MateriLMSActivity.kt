package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class MateriLMSActivity : AppCompatActivity() {
    lateinit var ivBack: ImageView
    lateinit var ivMore: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi_lmsactivity)

        //Inisiasi ketika more dipencet kembali ke halaman beranda LmsActivity
        ivMore.setOnClickListener {
            Intent(this, LmsActivity::class.java).also {
                startActivity(it)
            }
        }
        //Inisiasi ketika back dipencet kembali ke halaman beranda LmsActivity
        ivBack.setOnClickListener {
            Intent(this, LmsActivity::class.java).also {
                startActivity(it)
            }
        }

    }
}