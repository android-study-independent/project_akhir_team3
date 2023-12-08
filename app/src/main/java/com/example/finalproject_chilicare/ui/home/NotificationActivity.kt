package com.example.finalproject_chilicare.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.finalproject_chilicare.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val ivBack = findViewById<ImageView>(R.id.ivBack)
        ivBack.setOnClickListener {
            onBackPressed()
        }
    }
}