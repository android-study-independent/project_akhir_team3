package com.example.finalproject_chilicare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.Retro
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import com.example.finalproject_chilicare.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val fullname = findViewById<EditText>(R.id.txtFullname)
        val email = findViewById<EditText>(R.id.txtEmailRegister)
        val password = findViewById<EditText>(R.id.txtPasswordRegister)
        val confirmPassword = findViewById<EditText>(R.id.txtConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.buttonRegister)

        initAction()
    }

    fun initAction() {
        val btnRegister = findViewById<Button>(R.id.buttonRegister)

        btnRegister.setOnClickListener {
            postUser()
        }
    }

    fun postUser() {
        val fullname = findViewById<EditText>(R.id.txtFullname)
        val email = findViewById<EditText>(R.id.txtEmailRegister)
        val password = findViewById<EditText>(R.id.txtPasswordRegister)
       // val confirmPassword = findViewById<EditText>(R.id.txtConfirmPassword)

        val registerReq = RegisterRequest()
        registerReq.fullname = fullname.text.toString()
        registerReq.email = email.text.toString()
        registerReq.password = password.text.toString()

        val retro = Retro().getRetroClientInstance("http://192.168.43.94:1945/auth/").create(UserAPI::class.java)
        retro.createUser(registerReq).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val register = response.body()

                val textStatus = findViewById<TextView>(R.id.textStatus)
                val textMessage = findViewById<TextView>(R.id.textMessage)
                textStatus.text = register!!.status.toString()
                textMessage.text = register!!.message.toString()
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Failed", "Create User Failed")
            }
        })
    }

}

//        btnRegister.setOnClickListener {
//            val checkFullname = fullname.text.toString().trim()
//            val checkEmail = email.text.toString().trim()
//            val checkPassword = password.text.toString().trim()
//            val checkConfirmPassword = confirmPassword.text.toString().trim()
//
//            if (checkFullname.isEmpty()){
//                fullname.error = "Fullname required"
//                fullname.requestFocus()
//                return@setOnClickListener
//            }
//
//            else if (checkEmail.isEmpty()){
//                email.error = "Email required"
//                email.requestFocus()
//                return@setOnClickListener
//            }
//            else if (checkPassword.isEmpty()){
//                password.error = "password required"
//                password.requestFocus()
//                return@setOnClickListener
//            }
//            else if (checkConfirmPassword.isEmpty()){
//                confirmPassword.error = "confirmPassword required"
//                confirmPassword.requestFocus()
//                return@setOnClickListener
//            }
//
//            Network.instance.register(fullname.toString(), email.toString(), password.toString())
//                .enqueue(object : Callback<UserResponse>{
//                    override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
//                    }
//
//                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
//                    }
//                })
//            Log.d("Debug", "Register succes")
//        }

