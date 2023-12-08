package com.example.finalproject_chilicare.ui.home

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.ui.favorite.FavoriteFragment
import com.example.finalproject_chilicare.ui.lms.LmsFragment
import com.example.finalproject_chilicare.ui.login.LoginActivity
import com.example.finalproject_chilicare.ui.profile.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    lateinit var btnLogout: Button
    lateinit var sharedPreferences: SharedPreferences
    var isLoggedIn: Boolean = false
    lateinit var prefHelper: SharedPreferences


    private lateinit var buttonNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        buttonNav = findViewById(R.id.buttonNavbar)
        buttonNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.button_home -> {
                    displayFragment(HomeFragment())
                    true
                }

                R.id.button_fav -> {
                    displayFragment(FavoriteFragment())
                    true
                }

                R.id.button_lms -> {
                    displayFragment(LmsFragment())
                    true
                }

                R.id.button_profile -> {
                    displayFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }

        displayFragment(HomeFragment())


        Log.d("HomeActivity", "HomeActivity: Welcome to HomePage")


//button logut saya nonaktifkan dulu

//        btnLogout = findViewById(R.id.btnLogout)
//        btnLogout.setOnClickListener { doLogout() }
        //prefHelper = PreferencesHelper.customPrefs(this)
        prefHelper = PreferencesHelper.customPrefsHome(this)
        isLoggedIn = prefHelper.getBoolean(PreferencesHelper.KEY_IS_LOGIN, false)

    }

    private fun doLogout() {
        Log.d("HomeActivity", "Homeactivity: Logout berhasil")

        prefHelper.edit {
            remove(PreferencesHelper.KEY_TOKEN)
            putBoolean(PreferencesHelper.KEY_IS_LOGIN, false)
        }

        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.FrameDisplay, fragment).commit()
    }

}