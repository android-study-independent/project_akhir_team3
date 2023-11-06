package com.example.finalproject_chilicare.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject_chilicare.R

class FirstScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_first_screen, container, false)

        val buttonNext = activity?.findViewById<ViewPager2>(R.id.viewPagerOnboarding)

        view.findViewById<Button>(R.id.btnskip).setOnClickListener{
            buttonNext?.currentItem = 1
        }
        return view
    }


}