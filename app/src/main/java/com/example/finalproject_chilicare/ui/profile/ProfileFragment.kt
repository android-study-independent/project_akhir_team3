package com.example.finalproject_chilicare.ui.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.content.edit
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.ui.home.NotificationActivity
import com.example.finalproject_chilicare.ui.login.LoginActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class ProfileFragment : Fragment() {
    lateinit var btnLogout: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var prefHelper: SharedPreferences
    lateinit var cardNotifikasi1: CardView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Use safe call operator to handle nullable view
        view?.let { nonNullableView ->
            cardNotifikasi1 = nonNullableView.findViewById(R.id.CardNotifikasi)

            // Card ke activity Notification
            cardNotifikasi1.setOnClickListener {
                val intent = Intent(requireContext(), NotificationActivity::class.java)
                startActivity(intent)
            }
        }

        return view



        btnLogout = view.findViewById(R.id.btnKeluar)
        btnLogout.setOnClickListener { doLogout() }
        prefHelper = PreferencesHelper.customPrefs(LoginActivity())
    }

    private fun doLogout() {
        Log.d("HomeActivity", "Homeactivity: Logout berhasil")

        prefHelper.edit {
            remove(PreferencesHelper.KEY_TOKEN)
            putBoolean(PreferencesHelper.KEY_IS_LOGIN, false)
        }

        Intent(activity, LoginActivity::class.java).also {
            startActivity(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
