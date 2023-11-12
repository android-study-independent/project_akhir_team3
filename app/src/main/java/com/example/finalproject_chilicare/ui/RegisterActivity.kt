package com.example.finalproject_chilicare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var emailContainer: TextInputLayout
    lateinit var inputEmailRegister: EditText
    lateinit var usernameContainer: TextInputLayout
    lateinit var inputUsernameRegister: EditText
    lateinit var passwordContainer: TextInputLayout
    lateinit var inputPasswordRegister: EditText
    lateinit var confirmPasswordContainer: TextInputLayout
    lateinit var inputConfirmPasswordRegister: EditText
    lateinit var btnRegister: Button
    lateinit var textToLogin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        inputEmailRegister = findViewById(R.id.textInputEmailRegister)
        emailContainer = findViewById(R.id.emailContainer)
        usernameContainer = findViewById(R.id.usernameContainer)
        inputUsernameRegister = findViewById(R.id.textInputUsername)
        passwordContainer = findViewById(R.id.passwordContainer)
        inputPasswordRegister = findViewById(R.id.textInputPasswordRegister)
        confirmPasswordContainer = findViewById(R.id.confirmPasswordContainer)
        inputConfirmPasswordRegister = findViewById(R.id.textInputConfirmPassword)

        textToLogin = findViewById(R.id.textToLogin)

        textToLogin.setOnClickListener { Intent(this,LoginActivity::class.java).also {
            startActivity(it)
        } }

        checkEmail()
        checkUsername()
        checkPassword()
        checkConfirmPassword()
        initAction()
    }

    private fun checkEmail() {
        inputEmailRegister.setOnFocusChangeListener { _, hasFocus ->  
            if(!hasFocus){
                emailContainer.helperText = validEmail()

            }
        }
    }

    private fun validEmail(): String? {
        val txtEmail = inputEmailRegister.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
            return "Invalid Email Address"
        } else if (txtEmail.isEmpty()){
            return "Email must be entry"
        }
        return null
    }

    private fun checkUsername() {
        inputUsernameRegister.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                usernameContainer.helperText = validUsername()
            }
        }
    }

    private fun validUsername(): String? {
        val txtUsername = inputUsernameRegister.text.toString()

         if (txtUsername.isEmpty()){
            return "Username must be entry"
        }
        return null
    }

    private fun checkPassword() {
        inputPasswordRegister.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val txtPassword = inputPasswordRegister.text.toString()

        if (txtPassword.length < 8){
            return "Minimum character 8"
        }
        if (!txtPassword.matches((".*[A-Z].*".toRegex()))){
            return "Must 1 upper-case character"
        }
        if (!txtPassword.matches((".*[a-z].*".toRegex()))){
            return "Must 1 lower-case character"
        }
        if (!txtPassword.matches((".*[@#-_^].*".toRegex()))){
            return "Must 1 spesial character : @, #, -, _, ^"
        }
        return null
    }

    private fun checkConfirmPassword() {
        inputConfirmPasswordRegister.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                confirmPasswordContainer.helperText = validConfirmPassword()
            }
        }
    }

    private fun validConfirmPassword(): String? {
        val txtPassword = inputPasswordRegister.text.toString()
        val txtConfirmPassword = inputConfirmPasswordRegister.text.toString()

        if (txtConfirmPassword !=  txtPassword){
            return "Your confirm password not same"
        }
        return null
    }


    fun initAction() {
        val btnRegister = findViewById<Button>(R.id.buttonRegister)

        btnRegister.setOnClickListener {
            createNewUser()
        }
    }

    fun createNewUser() {
        inputEmailRegister = findViewById(R.id.textInputUsername)
        inputEmailRegister= findViewById(R.id.textInputEmailRegister)
        inputPasswordRegister = findViewById(R.id.textInputPasswordRegister)

        val registerReq = RegisterRequest()
        registerReq.fullname = inputEmailRegister.text.toString()
        registerReq.email = inputEmailRegister.text.toString()
        registerReq.password = inputPasswordRegister.text.toString()

        val retro = Network().getRetroClientInstance("https://35b3-103-189-201-221.ngrok-free.app/auth/").create(UserAPI::class.java)
        retro.createUser(registerReq).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val register = response.body()

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

