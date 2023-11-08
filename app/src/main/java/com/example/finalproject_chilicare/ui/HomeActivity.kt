package com.example.finalproject_chilicare.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.finalproject_chilicare.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Ini untuk nampilin log aja kalo homeactivity dibuka
        Log.d("HomeActivity :", "HomeActivity : barusan dibuka nih")

        // Ini untuk penamaan variabelnya
        val btnLogout = findViewById<Button>(R.id.btnLogout)


        btnLogout.setOnClickListener {
            // Ini ketika diteken, manggil fungsi performLogout
            postLogout()
        }

        // Ini untuk mengindikasi user udah login apa belom
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {

            // Ini jika user belom login, diarahkan ke Loginactivity
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }

        }
    }

    private fun postLogout() {

        // Ini untuk nampilin log logout ajaa
        Log.d("HomeActivity", "Homeactivity : Logout berhasil")

        // Ini untuk menghapus tokennya
        clearTokenFromSharedPreferences()

        // Ini untuk mengindikasi kalo user udah login
        setLoggedInStatus(false)

        // Ini untuk diarahkan ke LoginActivity
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    // Ini fungsi untuk menghapus tokennya
    private fun clearTokenFromSharedPreferences() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove("accessToken")
        editor.apply()
    }

    // Ini fungsi untuk mengindikasi user udah login atau belum
    private fun setLoggedInStatus(isLoggedIn: Boolean) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }
}