package com.example.finalproject_chilicare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.Retro
import com.example.finalproject_chilicare.data.api.Routes
import com.example.finalproject_chilicare.data.response.UserRequest
import com.example.finalproject_chilicare.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        loginActivities()
    }

//    private fun loginActivities() {
//        val btnLogin = findViewById<Button>(R.id.btnLogin)
//
//        btnLogin.setOnClickListener {
//            login()
//        }
    }

    private fun login() {
        val req = UserRequest()
//        val txtEmail = findViewById<EditText>(R.id.txtEmail)
//        val txtPassword = findViewById<EditText>(R.id.txtPassword)

//        req.email = txtEmail.text.toString().trim()
//        req.password = txtPassword.text.toString().trim()

 //       val retro = Retro().getRetroClientInstance().create(Routes::class.java)
//        retro.login(req).enqueue(object  : Callback<UserResponse>{
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                val user = response.body()
//
////                user!!.data?.email?.let { Log.d("Debug", "hasil ${it}" ) }
////                user!!.data?.token?.let { Log.d("Debug", it) }
//
//
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                Log.e("Error", "Error login")
//            }
//
//        })
    }

}