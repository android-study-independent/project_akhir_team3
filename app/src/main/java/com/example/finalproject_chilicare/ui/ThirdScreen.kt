package com.example.finalproject_chilicare.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.findNavController
//import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject_chilicare.R

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val btnLogin = view?.findViewById<Button>(R.id.btnLoginSplashscreen)
        val btnRegister = view?.findViewById<Button>(R.id.btnRegisterSplashscreen)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

//
//        view.findViewById<Button>(R.id.btnlogin).setOnClickListener {
//            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
//            onBoardingFinished()
//
//        }

        view.findViewById<Button>(R.id.btnRegisterSplashscreen).setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_registerActivity)
            onBoardingFinished()

        }

        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}