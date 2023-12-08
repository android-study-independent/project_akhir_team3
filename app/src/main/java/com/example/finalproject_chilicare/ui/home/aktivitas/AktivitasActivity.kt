package com.example.finalproject_chilicare.ui.home.aktivitas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.ui.home.HomeActivity

class AktivitasActivity : AppCompatActivity() {

    lateinit var buttonBack : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aktivitas)

        buttonBack = findViewById(R.id.ivBackaktivitas)

        // button back to home
        buttonBack.setOnClickListener { Intent(this,HomeActivity::class.java).also {
            startActivity(it)
        } }

    }
}