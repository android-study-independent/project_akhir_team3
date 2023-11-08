package com.example.finalproject_chilicare.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.content.Context
import android.util.Patterns
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Retro
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.LoginRequest
import com.example.finalproject_chilicare.data.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Ini untuk melihat status login, menggunakan shared preferences
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // Ini jika user sudah login, maka arahkan ke HomeActivity
            Intent(this, HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        } else {
            // Ini jika user belum login, maka arahkan ke LoginActivity
            setContentView(R.layout.activity_login)

            // Ini penamaan variabelnya
            val btnDaftar = findViewById<TextView>(R.id.textviewdaftardisini)
            val email = findViewById<EditText>(R.id.textInputEmailLogin)
            val password = findViewById<EditText>(R.id.textInputPasswordLogin)
            val btnLogin = findViewById<Button>(R.id.btnmasuk)

            btnDaftar.setOnClickListener {
                Intent(this, RegisterActivity::class.java).also {
                    startActivity(it)
                }
            }

            btnLogin.setOnClickListener {
                // Ini ketika diteken, manggil fungsi postLogin
                postLogin(email.text.toString(), password.text.toString())
            }
        }
    }

    // Ini fungsi dari postLogin
    private fun postLogin(email: String, password: String) {
        val loginReq = LoginRequest(
            requestEmail = email,
            requestPassword = password
        )

        val retro = Retro().getRetroClientInstance("https://6f38-103-189-201-221.ngrok-free.app/auth/")
            .create(UserAPI::class.java)

        retro.getUser(loginReq).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val message = loginResponse.message
                        Log.d("Login", "Loginactivity respon : $message")

                        // Ini memanggil token respon dari API
                        val token = loginResponse.data?.token
                        if (!token.isNullOrEmpty()) {
                            Log.d("Login", "Loginactivity token : $token")

                            // Ini simpan token di shared prefrences
                            saveTokenInSharedPreferences(token)

                            // Ini untuk mengindikasi kalo user udah login
                            setLoggedInStatus(true)

                            // Ini untuk diarahkan ke HomeActivity
                            goToHomeActivity()

                            // Ini menampilkan toast berhasil setelah mencet tombol login
                            val successMessage = "Berhasil login sebagai $email" // Berdasarkan dari email
                            Toast.makeText(this@LoginActivity, successMessage, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("Login", "Respon body tidak meenemukan data tokennya")
                    }
                } else {
                    Log.d("Login", "pesan eror: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Login", "pesan eror: ${t.message}")
            }
        })
    }

    // Ini fungsi untuk nyimpen tokennya
    private fun saveTokenInSharedPreferences(token: String) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("accessToken", token)
        editor.apply()
    }

    // Ini fungsi untuk mengindikasi user udah login atau belum
    private fun setLoggedInStatus(isLoggedIn: Boolean) {
        val sharedPreferences: SharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    // Ini fungsi untuk menuju ke halaman Homeactivity
    private fun goToHomeActivity() {
        Intent(this, HomeActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

}