package com.example.finalproject_chilicare.ui.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.ui.home.NotificationActivity
import com.example.finalproject_chilicare.ui.login.LoginActivity
import com.example.finalproject_chilicare.ui.onboarding.OnboardingActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private lateinit var btnLogout: Button
    private lateinit var cardNotifikasi1: CardView
    private lateinit var prefHelper: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inisialisasi view yang tidak nullable
        cardNotifikasi1 = view.findViewById(R.id.CardNotifikasi)
        btnLogout = view.findViewById(R.id.btnKeluar)
        prefHelper = requireActivity().getSharedPreferences("chilicare_preference", Context.MODE_PRIVATE)

        // Card ke activity Notification
        cardNotifikasi1.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

        // Logout Button Click Listener
        btnLogout.setOnClickListener { doLogout() }

        return view
    }

    private fun doLogout() {
        Log.d("ProfileActivity", "Profileactivity: Logout berhasil")
        prefHelper.edit {
            remove(PreferencesHelper.KEY_TOKEN)
            putBoolean(PreferencesHelper.KEY_IS_LOGIN, false)
        }

        // Redirect ke LoginActivity setelah logout
        Log.d("ProfileFragment", "Logging out...")
        Log.d("ProfileFragment", "Redirecting to LoginActivity")
        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finishAffinity()
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