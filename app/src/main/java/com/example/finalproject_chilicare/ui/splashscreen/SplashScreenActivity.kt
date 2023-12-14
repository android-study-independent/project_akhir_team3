package com.example.finalproject_chilicare.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.ui.onboarding.OnboardingActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        progressBar = findViewById(R.id.progressbarsplash)

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.VISIBLE
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
        progressBar.visibility = View.GONE
    }
}