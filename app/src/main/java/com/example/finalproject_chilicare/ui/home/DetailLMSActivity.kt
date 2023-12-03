package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class DetailLMSActivity : AppCompatActivity() {
    lateinit var iv_Kembali: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lmsactivity)

        //Tombol untuk kembali
        iv_Kembali.setOnClickListener {
            Intent(this, MateriLMSActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}