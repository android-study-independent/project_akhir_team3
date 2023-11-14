package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.edit
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.ui.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    lateinit var btnLogout: Button
    lateinit var sharedPreferences: SharedPreferences
    var isLoggedIn: Boolean = false
    lateinit var prefHelper: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Log.d("HomeActivity", "HomeActivity: barusan dibuka nih")

        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener { doLogout() }
        prefHelper = PreferencesHelper.customPrefs(this)
        isLoggedIn = sharedPreferences.getBoolean(PreferencesHelper.KEY_IS_LOGIN, false)

    }

    private fun doLogout() {
        Log.d("HomeActivity", "Homeactivity: Logout berhasil")

        prefHelper.edit {
            remove(PreferencesHelper.KEY_TOKEN)
            putBoolean(PreferencesHelper.KEY_IS_LOGIN, false)
        }

        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}