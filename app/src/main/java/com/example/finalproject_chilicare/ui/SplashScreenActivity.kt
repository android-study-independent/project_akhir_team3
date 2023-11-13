package com.example.finalproject_chilicare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.finalproject_chilicare.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this,ViewPagerFragment::class.java)
//            startActivity(intent)
//            finish()
//        },5000)
    }
}