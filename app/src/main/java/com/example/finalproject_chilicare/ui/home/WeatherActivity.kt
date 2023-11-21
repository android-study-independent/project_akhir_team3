package com.example.finalproject_chilicare.ui.home

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.finalproject_chilicare.R

class WeatherActivity : AppCompatActivity() {
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isLocationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->
            isLocationPermissionGranted = permission[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranted
        }

        askPermission()

    }


    private fun askPermission() {
        isLocationPermissionGranted = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val askPermission : MutableList<String> = ArrayList()

        if (!isLocationPermissionGranted) {
            askPermission.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (askPermission.isNotEmpty()) {
            permissionLauncher.launch(askPermission.toTypedArray())
        }



    }
}