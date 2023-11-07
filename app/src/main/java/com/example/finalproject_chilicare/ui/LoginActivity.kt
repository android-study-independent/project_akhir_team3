package com.example.finalproject_chilicare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Retro
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.LoginRequest
import com.example.finalproject_chilicare.data.response.LoginResponse
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val btnDaftar = findViewById<TextView>(R.id.textviewdaftardisini)
        val email = findViewById<EditText>(R.id.textInputEmailLogin)
        val password = findViewById<EditText>(R.id.textInputPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btnmasuk)

        btnDaftar.setOnClickListener {
            Intent(this,RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        btnLogin.setOnClickListener {
            Intent(this,HomeActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    fun postLogin() {
        val email = findViewById<EditText>(R.id.textInputEmailLogin)
        val password = findViewById<EditText>(R.id.textInputPasswordLogin)

        val loginReq = LoginRequest(
            requestEmail = email.text.toString(),
            requestPassword = password.text.toString()
        )

        val retro =
            Retro().getRetroClientInstance("https://6f38-103-189-201-221.ngrok-free.app/auth/")
                .create(UserAPI::class.java)
        retro.getUser(loginReq).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val message = loginResponse.message
                        Log.d("Login", "respon body: $message")
                    } else {
                        Log.d("Login", "respon body gaada")
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

}