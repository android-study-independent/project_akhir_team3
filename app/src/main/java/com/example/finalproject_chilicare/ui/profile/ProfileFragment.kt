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
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.finalproject_chilicare.R
import com.example.finalproject_chilicare.data.PreferencesHelper
import com.example.finalproject_chilicare.data.api.ApiInterface
import com.example.finalproject_chilicare.data.api.Network
import com.example.finalproject_chilicare.data.response.login.LoginResponse
import com.example.finalproject_chilicare.ui.home.HomeActivity
import com.example.finalproject_chilicare.ui.home.NotificationActivity
import com.example.finalproject_chilicare.ui.login.LoginActivity
import com.example.finalproject_chilicare.ui.onboarding.OnboardingActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment() {
    private lateinit var btnLogout: Button
    private lateinit var cardNotifikasi1: CardView
    private lateinit var prefHelper: SharedPreferences
    private lateinit var btnback : ImageView
    lateinit var fullnameProfile : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // return inflater.inflate(R.layout.fragment_profile, container, false)

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        btnback = view.findViewById(R.id.ivBackProfile)
        //button back home
        btnback.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
        }



        // Inisialisasi view yang tidak nullable
        cardNotifikasi1 = view.findViewById(R.id.CardNotifikasi)
        btnLogout = view.findViewById(R.id.btnKeluar)
        prefHelper = requireActivity().getSharedPreferences("chilicare_preference", Context.MODE_PRIVATE)
        fullnameProfile = view.findViewById(R.id.tvAvatarProfile)

        // Card ke activity Notification
        cardNotifikasi1.setOnClickListener {
            val intent = Intent(requireContext(), NotificationActivity::class.java)
            startActivity(intent)
        }

//        val fullname = arguments?.getString("fullname")
//
//        Log.d("fullname", "$fullname")

//        getNameProfile()


        // Logout Button Click Listener
        btnLogout.setOnClickListener { doLogout() }

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val fullname = arguments?.getString("fullname")
//
//
//        if (!fullname.isNullOrEmpty()) {
//
//            fullnameProfile.text = fullname
//        }
//
//
//    }



//    private fun getNameProfile() {
//
//        val retro = Network().getRetroClientInstance().create(ApiInterface::class.java)
//
//        retro.getFullnameLogin().enqueue(object : Callback<LoginResponse>{
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val loginResponse = response.body()!!
//
//                    // Pastikan fullnameProfile tidak null sebelum mengakses properti fullname
//                    loginResponse.fullname?.let {
//                        fullnameProfile.text = it
//                        Log.d("Fullname", it)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }

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