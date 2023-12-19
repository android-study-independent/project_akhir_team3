package com.example.finalproject_chilicare.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.forum.ForumResponse
import com.example.finalproject_chilicare.data.response.login.LoginRequest
import com.example.finalproject_chilicare.data.response.login.LoginResponse
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.ui.home.HomeFragment
import com.example.finalproject_chilicare.ui.register.RegisterActivity
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var loginContainer : TextInputLayout
    lateinit var passwordContainer : TextInputLayout
    lateinit var loginEmail: EditText
    lateinit var loginPassword: EditText
    lateinit var tvLupaPassword: TextView
    lateinit var btLogin: Button
    lateinit var tvDaftarDisini: TextView
    lateinit var prefHelper: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.etEmail)
        loginPassword = findViewById(R.id.etPassword)
        tvLupaPassword = findViewById(R.id.tvLupaPassword)
        btLogin = findViewById(R.id.btMasuk)
        tvDaftarDisini = findViewById(R.id.tvDaftarDisini)
        loginContainer = findViewById(R.id.emailLoginContainer)
        passwordContainer = findViewById(R.id.passwordLoginContainer)


        loginEmail.setText("rifliansahmutiara@gmail.com")
        loginPassword.setText("@mutiara03")

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

        loginContainer.helperText = null
        passwordContainer.helperText = null

        checkEmail()
        checkPassword()
        initAction()
    }

    //INVALID LOGIN
    private fun checkEmail() {
        loginEmail.setOnFocusChangeListener { _, hasFocus ->
            val textInputLayout = findViewById<TextInputLayout>(R.id.emailLoginContainer)
            if (!hasFocus) {
                textInputLayout.helperText = invalidEmail()
            }
        }
    }

    private fun invalidEmail(): String? {
        val txtEmail = loginEmail.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()) {
            return "Email yang anda masukan salah"
        } else if (txtEmail.isEmpty()) {
            return "Masukan Alamat Email"
        }
        return null
    }

    private fun checkPassword() {
        loginPassword.setOnFocusChangeListener { _, hasFocus ->
            val textInputLayout = findViewById<TextInputLayout>(R.id.passwordLoginContainer)
            if (!hasFocus) {
                textInputLayout.helperText = invalidPassword()
            }
        }
    }

    private fun invalidPassword(): String? {
        val txtPassword = loginPassword.text.toString()

        if (txtPassword.length < 10) {
            return "Minimal 10 karakter"
        }
        if (!txtPassword.matches((".*[a-z].*".toRegex()))) {
            return "Harus ada 1 karakter huruf kecil"
        }
        if (!txtPassword.matches((".*[@#-_^].*".toRegex()))) {
            return "Harus ada karakter spesial : @, #, -, _, ^"
        }
        return null
    }

    fun initAction() {
        val btLogin = findViewById<Button>(R.id.btMasuk)

        btLogin.setOnClickListener {
            doLogin()
        }
    }

    companion object {
        var userFullname: String? = null
        var userEmail: String? = null
    }

    private fun doLogin() {
        val enteredEmail = loginEmail.text.toString()
        val enteredPassword = loginPassword.text.toString()

        val errorEmail = invalidEmail()
        val errorPassword = invalidPassword()

        if (errorEmail == null && errorPassword == null) {
            val loginReq = LoginRequest(requestEmail = enteredEmail, requestPassword = enteredPassword)

            val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)

            retro.userLogin(loginReq).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.data?.token?.let { token ->
                            if (token.isNotEmpty()) {
                                // Mendapatkan fullname dari respons
                                val fullname = response.body()!!.fullname
                                val email = response.body()!!.email

                                // Menyimpan fullname ke dalam objek companion di LoginActivity
                                userFullname = fullname
                                userEmail = email

                                // Menampilkan pesan Toast dengan fullname
                                showToast(this@LoginActivity, "Selamat datang, $fullname! 👋", Toast.LENGTH_LONG)

                                // Menyiapkan Intent
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                                // Menambahkan fullname sebagai extra dalam Intent
                                intent.putExtra("fullname", fullname)
                                intent.putExtra("email", email)

                                // Memulai aktivitas baru
                                startActivity(intent)

                                // Navigasi ke layar beranda
                                navigateToHome()

                                // Menyimpan data login
                                if (fullname != null && email != null) {
                                    saveLoginData(token, fullname, email)
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Email atau password salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Display error messages for email and password
            loginEmail.error = errorEmail
            loginPassword.error = errorPassword
        }
    }

    private fun saveLoginData(token: String, fullname: String, email: String) {
        prefHelper.edit().apply {
            putString(PreferencesHelper.KEY_TOKEN, token)
            putString(PreferencesHelper.KEY_FULLNAME, fullname)
            putString(PreferencesHelper.KEY_EMAIL, email)
            putBoolean(PreferencesHelper.KEY_IS_LOGIN, true)
            apply()
        }
    }



    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        val toast = Toast.makeText(context, message, duration)
        toast.show()
    }
}