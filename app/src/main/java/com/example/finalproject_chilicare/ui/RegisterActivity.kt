package com.example.finalproject_chilicare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var namaPengguna : EditText
    lateinit var password : EditText
    lateinit var konfirmasiPassword : EditText
    lateinit var btRegister : Button
    lateinit var tvLoginDisini : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.etEmail)
        namaPengguna = findViewById(R.id.etNamaPengguna)
        password = findViewById(R.id.etPassword)
        konfirmasiPassword = findViewById(R.id.etKonfirmasiPassword)
        btRegister = findViewById(R.id.btDaftarSekarang)
        tvLoginDisini = findViewById(R.id.tvLoginDisini)

        tvLoginDisini.setOnClickListener { Intent(this,LoginActivity::class.java).also {
            startActivity(it)
        } }

        initAction()
    }

    fun initAction() {
        btRegister.setOnClickListener {
            postUser()
        }
    }

    fun postUser() {
        val registerReq = RegisterRequest()

        registerReq.fullname = namaPengguna.text.toString()
        registerReq.email = email.text.toString()
        registerReq.password = password.text.toString()

        val retro = Network().getRetroClientInstance("http://195.35.32.179:8003/auth/").create(UserAPI::class.java)
        retro.createUser(registerReq).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val register = response.body()

//                val textStatus = findViewById<TextView>(R.id.textStatus)
//                val textMessage = findViewById<TextView>(R.id.textMessage)
//                textStatus.text = register!!.status.toString()
//                textMessage.text = register!!.message.toString()
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Failed", "Create User Failed")
            }
        })

        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()

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

