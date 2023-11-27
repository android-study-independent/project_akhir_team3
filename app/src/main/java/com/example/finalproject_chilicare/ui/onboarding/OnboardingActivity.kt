package com.example.finalproject_chilicare.ui.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.adapter.OnboardingAdapter
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.dataclass.OnboardingData
import com.example.finalproject_chilicare.ui.login.LoginActivity
import com.example.finalproject_chilicare.ui.register.RegisterActivity
import com.zhpan.indicator.IndicatorView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class OnboardingActivity : AppCompatActivity() {

    lateinit var viewPager : ViewPager2
    lateinit var adapter: OnboardingAdapter
    lateinit var indikator : IndicatorView
    lateinit var ButtonMasuk : Button
    lateinit var ButtonDaftar : Button
    lateinit var prefHelper: SharedPreferences

    private var listdata =
        listOf(
            OnboardingData(
                title = "Selamat Datang Di Chili Care",
                desc =  "Chilli Care media untuk kita semua belajar \n menanam cabai dari pemula sampai dengan menghasilkan Cabai yang berkualitas.",
                image = R.drawable.screen1
            ),
            OnboardingData(
                title = "Tanam, Panen Berkualitas",
                desc =  "Mari Bergabung dan Mulai Belajar Bersama Kami.",
                image = R.drawable.screen2fix
            )
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPagertv)

        adapter = OnboardingAdapter(this,listdata)

        viewPager.adapter = adapter

        indikator = findViewById(R.id.indicator_view)

        indikator.apply {
            setSliderWidth(resources.getDimension(R.dimen.space_10))
            setSliderHeight(resources.getDimension(R.dimen.space_5))
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.CIRCLE)
            setupWithViewPager(viewPager)
        }

        // Inisialisasi Shared Preferences
        prefHelper = PreferencesHelper.customPrefOnboarding(this)

        if (isLoggedIn()) {
            redirectToLogin()
        }
        if (isRegistIn()) {
            redirectToRegist()
        }

        ButtonMasuk = findViewById(R.id.btnloginonboarding)
        ButtonDaftar = findViewById(R.id.btndaftaronboarding)

        ButtonMasuk.setOnClickListener {
            saveLoginStatus(true)

            //pindah halaman login
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()

        }
        ButtonDaftar.setOnClickListener {
            saveRegistPage(true)

            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isLoggedIn(): Boolean {
        // Baca status login dari Shared Preferences
        return prefHelper.getBoolean(PreferencesHelper.KEY_LOGGED_IN, false)
    }

    private fun isRegistIn(): Boolean {
        // Baca status login dari Shared Preferences
        return prefHelper.getBoolean(PreferencesHelper.KEY_REGIST_IN, false)
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        // Simpan status login ke Shared Preferences
        val editor = prefHelper.edit()
        editor.putBoolean(PreferencesHelper.KEY_LOGGED_IN, isLoggedIn)
        editor.apply()
    }

    private fun saveRegistPage(isRegistIn: Boolean) {
        // Simpan status daftar ke Shared Preferences
        val editor = prefHelper.edit()
        editor.putBoolean(PreferencesHelper.KEY_REGIST_IN, isRegistIn)
        editor.apply()
    }

    private fun redirectToLogin() {
        // Pindah ke halaman home atau halaman utama aplikasi
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun redirectToRegist() {
        // Pindah ke halaman home atau halaman utama aplikasi
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}