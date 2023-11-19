package com.example.finalproject_chilicare.ui.forgetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.finalproject_chilicare.R

class OTPActivity : AppCompatActivity() {

    lateinit var btnSendOTP: Button
    lateinit var btnBack : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_activity)

        btnSendOTP = findViewById(R.id.btnkirimotp)
        btnBack = findViewById(R.id.btnkembali)
    }
}