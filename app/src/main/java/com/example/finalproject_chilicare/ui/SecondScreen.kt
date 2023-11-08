package com.example.finalproject_chilicare.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.finalproject_chilicare.R


class SecondScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_second_screen, container, false)

        view.findViewById<Button>(R.id.loginmasuk).setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            onBoardingFinished()
        }

        view.findViewById<Button>(R.id.buttondaftar1).setOnClickListener {
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