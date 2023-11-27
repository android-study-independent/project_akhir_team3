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


    private lateinit var mfusedlocation: FusedLocationProviderClient
    private var myRequestCode = 1010
    private lateinit var locationCallback: LocationCallback
    private var lat = ""
    private var lon = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                myRequestCode
            )
        }
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val lastLocation: Location = p0.lastLocation!!
                lat = lastLocation.latitude.toString()
                lon = lastLocation.longitude.toString()

            }
        }
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {


        mfusedlocation.lastLocation.addOnSuccessListener { location: Location? ->

            if (location != null) {
                lat = location.latitude.toString()
                lon = location.latitude.toString()

                Log.d("Latlon", "LocationData: ${lat}, ${lon}")

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, OnboardingActivity::class.java)
                    intent.putExtra("lat", lat)
                    intent.putExtra("lon", lon)
                    startActivity(intent)
                    finish()
                }, 3000)
            } else {
                NewLocation()
            }
        }
    }

        @SuppressLint("MissingPermission")
        private fun NewLocation() {
            val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000
            }
            mfusedlocation.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == myRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            } else{
                Toast.makeText(this, "Location permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}