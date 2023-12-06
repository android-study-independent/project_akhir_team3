package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.utils.OnTabClickListener

class AktivitasActivity : AppCompatActivity() {

    lateinit var buttonBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivitas)

        buttonBack = findViewById(R.id.ivBacklms)



        buttonBack.setOnClickListener {
            Log.d("aktivitas","hasilnnya")
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)

            }
        }

    }
}