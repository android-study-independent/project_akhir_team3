package com.example.finalproject_chilicare.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.LoginRequest
import com.example.finalproject_chilicare.data.response.LoginResponse
import com.example.finalproject_chilicare.ui.HomeActivity
import com.example.finalproject_chilicare.ui.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var password: EditText
    lateinit var tvLupaPassword: TextView
    lateinit var btLogin: Button
    lateinit var tvDaftarDisini: TextView
    lateinit var prefHelper: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email = findViewById(R.id.etEmail)
        password = findViewById(R.id.etPassword)
        tvLupaPassword = findViewById(R.id.tvLupaPassword)
        btLogin = findViewById(R.id.btMasuk)
        tvDaftarDisini = findViewById(R.id.tvDaftarDisini)

        prefHelper = PreferencesHelper.customPrefs(this)

        // Check login status
        if (prefHelper.getBoolean(PreferencesHelper.KEY_IS_LOGIN, false)) {
            navigateToHome()
        }

        tvDaftarDisini.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        btLogin.setOnClickListener {
            doLogin(email.text.toString(), password.text.toString())
        }
    }

    private fun doLogin(email: String, password: String) {
        val loginReq = LoginRequest(requestEmail = email, requestPassword = password)

        val retro = Network().getRetroClientInstance("http://195.35.32.179:8003/auth/")
            .create(UserAPI::class.java)

        retro.userLogin(loginReq).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.data?.token?.let { token ->
                        if (token.isNotEmpty()) {
                            saveLoginData(token)
                            navigateToHome()
                        }
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Email atau password salah", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveLoginData(token: String) {
        prefHelper.edit().apply {
            putString(PreferencesHelper.KEY_TOKEN, token)
            putBoolean(PreferencesHelper.KEY_IS_LOGIN, true)
            apply()
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}