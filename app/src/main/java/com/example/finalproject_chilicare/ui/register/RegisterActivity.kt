package com.example.finalproject_chilicare.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.api.UserAPI
import com.example.finalproject_chilicare.data.response.RegisterRequest
import com.example.finalproject_chilicare.data.response.RegisterResponse
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.ui.login.LoginActivity
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
        btnRegister = findViewById(R.id.buttonRegister)

        textToLogin = findViewById(R.id.textToLogin)

        textToLogin.setOnClickListener { Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        } }


        initAction()
    }

    fun initAction() {
        btnRegister.setOnClickListener {
            checkEmail()
            checkUsername()
            checkPassword()
            checkConfirmPassword()

            createNewUser()

        }
    }

    fun createNewUser() {
        val registerReq = RegisterRequest()
        registerReq.fullname = inputEmailRegister.text.toString()
        registerReq.email = inputEmailRegister.text.toString()
        registerReq.password = inputPasswordRegister.text.toString()

        val retro = Network().getRetroClientInstance("http://195.35.32.179:8003/auth/").create(UserAPI::class.java)
        retro.createUser(registerReq).enqueue(object : Callback<RegisterResponse>{
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val register = response.body()

                if (response.isSuccessful){
                    // Cek hasil register
//                    val textStatus = findViewById<TextView>(R.id.textWelcomeToChiliicare)
//                    val textMessage = findViewById<TextView>(R.id.textPreviewChiliicare)
//                    textStatus.text = register!!.status.toString()
//                    textMessage.text = register!!.message.toString()
                    checkEmail()
                    checkUsername()
                    checkPassword()
                    checkConfirmPassword()
                    clearText()
                    Toast.makeText(this@RegisterActivity,"${register?.message}", Toast.LENGTH_SHORT).show()
                    moveToLogin()
                }
                else {
                    Log.d("Email sama", "${register?.message}")
                }

            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Failed", "Create User Failed")
            }
        })
    }

    private fun clearText() {
        inputEmailRegister.text = null
        inputUsernameRegister.text = null
        inputPasswordRegister.text = null
        inputConfirmPasswordRegister.text = null
    }

    private fun moveToLogin(){
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
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
            inputEmailRegister.requestFocus()
        } else if (txtEmail.isEmpty()){
            return "Email must be entry"
            inputEmailRegister.requestFocus()
        }
        else {
            val checkSameEmail = CheckInputRegister(txtEmail) { isEmailUsed ->
                if (isEmailUsed){
                    return@CheckInputRegister
                }
            }
            checkSameEmail.execute()
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
             inputUsernameRegister.requestFocus()

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
            inputPasswordRegister.requestFocus()
        }
        if (!txtPassword.matches((".*[A-Z].*".toRegex()))){
            return "Must 1 upper-case character"
            inputPasswordRegister.requestFocus()
        }
        if (!txtPassword.matches((".*[a-z].*".toRegex()))){
            return "Must 1 lower-case character"
            inputPasswordRegister.requestFocus()
        }
        if (!txtPassword.matches((".*[@#!_^].*".toRegex()))){
            return "Must 1 spesial character : @, #, !, _, ^"
            inputPasswordRegister.requestFocus()
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
            inputConfirmPasswordRegister.requestFocus()
        }
        return null
    }




}

